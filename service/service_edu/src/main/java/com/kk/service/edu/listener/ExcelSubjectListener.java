package com.kk.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.service.edu.mapper.SubjectMapper;
import com.kk.service.edu.pojo.Subject;
import com.kk.service.edu.pojo.excel.ExcelSubjectData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSubjectListener implements ReadListener<ExcelSubjectData> {

    private static final int BATCH_COUNT = 100;

    private SubjectMapper subjectMapper;
    private ExcelSubjectData excelSubjectData;
    private Set<String> cachedDataSet = new HashSet<>(); // 缓存的数据

    public ExcelSubjectListener(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext context) {
        log.info("解析到一条数据:{}", excelSubjectData);
        String levelOneTitle = excelSubjectData.getLevelOneTitle() + ":0";
        String levelTwoTitle = levelOneTitle.substring(0, levelOneTitle.length() - 1) + excelSubjectData.getLevelTwoTitle();
        if (!cachedDataSet.contains(levelOneTitle)) cachedDataSet.add(levelOneTitle);
        if (!cachedDataSet.contains(levelTwoTitle)) cachedDataSet.add(levelTwoTitle);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataSet.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataSet = SetUtils.newIdentityHashSet();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataSet.size());
        for (String s : cachedDataSet) {
            log.info(s);
            String parent_id = "0";
            String[] subjectName = s.split(":");
            Subject subjectFather = this.getByTitle(subjectName[0]);
            // 查询数据库中是否有该一级分类，如果没有则存储于数据库，然后直接跳过后面的循环，进行下一个集合元素的遍历
            // 因为这里的数据结构使用的是 TreeSet 又因为根据肯定先有 1 级分类才会有 2 级分类，所以当出现了 2 级分类则肯定集合中已经存储了 1 级分类
            if (subjectFather == null) {
                Subject subject = new Subject();
                subject.setTitle(subjectName[0]);
                subject.setParentId(parent_id);
                subjectMapper.insert(subject);
                parent_id = subject.getId();
            }else {
                parent_id = subjectFather.getId();
            }
            if (!"0".equals(subjectName[1])) {
                //判断 2 级分类是否存在，不存在才存储
                Subject subject = this.getSubByTitle(subjectName[1], parent_id);
                if (subject == null) {
                    subject = new Subject();
                    subject.setTitle(subjectName[1]);
                    subject.setParentId(parent_id);
                    subjectMapper.insert(subject);
                }
            }
        }
        log.info("存储数据库成功！");
    }

    private Subject getByTitle(String title) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", 0);
        return subjectMapper.selectOne(queryWrapper);
    }

    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return subjectMapper.selectOne(queryWrapper);
    }
}