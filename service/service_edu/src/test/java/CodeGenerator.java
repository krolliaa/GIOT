import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.kk.service.base.model.BasePojo;
import com.kk.service.edu.pojo.Teacher;
import org.junit.jupiter.api.Test;

public class CodeGenerator {

    @Test
    public void genCode() {
        FastAutoGenerator.create("jdbc:mysql://192.168.56.1:3307/giot_edu?useSSL=false&serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("kk") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir()
                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.kk.service") // 设置父包名
                            .moduleName("edu") // 设置父包模块名
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper");
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("edu_") // 设置过滤表前缀
                            .entityBuilder()
                            .superClass(BasePojo.class)
                            .enableTableFieldAnnotation()
                            .enableChainModel()
                            .enableLombok()
                            .enableRemoveIsPrefix()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .logicDeleteColumnName("is_deleted") //设置逻辑删除属性名（实体）
                            .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                            .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE))
                            .idType(IdType.ASSIGN_ID) //主键策略
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .controllerBuilder()
                            .enableHyphenStyle()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test
    public void test() {
        System.out.println(System.getProperty("user.dir"));
        Teacher teacher = new Teacher();
        System.out.println(teacher.getGmtModified());
        System.out.println(teacher.toString());
    }
}