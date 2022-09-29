package com.kk.service.edu.controller.api;

import com.kk.common.result.ResultData;
import com.kk.common.util.JWTInfo;
import com.kk.common.util.JWTUtils;
import com.kk.service.edu.pojo.vo.CourseCollectReturnVo;
import com.kk.service.edu.service.CourseCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(description = "课程收藏用户页面接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "api/edu/course-collect")
public class ApiCourseCollectController {

    @Autowired
    private CourseCollectService courseCollectService;

    @ApiOperation(value = "判断该用户是否收藏了该门课程")
    @GetMapping(value = "/auth/is-collect/{courseId}")
    public ResultData isCollectThisCourse(@ApiParam(value = "课程ID") @PathVariable(value = "courseId") String courseId, HttpServletRequest httpServletRequest) {
        //判断用户登录状态是否正常
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        Boolean isCollect = courseCollectService.isCollectThisCourse(courseId, jwtInfo.getId());
        return ResultData.ok().data("isCollect", isCollect);
    }

    @ApiOperation(value = "收藏课程")
    @PostMapping(value = "/auth/addCollect/{courseId}")
    public ResultData addCollect(@ApiParam(value = "课程ID") @PathVariable(value = "courseId") String courseId, HttpServletRequest httpServletRequest) {
        //判断用户登录状态是否正常
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        Boolean addCollect = courseCollectService.addCollect(courseId, jwtInfo.getId());
        if (addCollect) return ResultData.ok().message("收藏成功");
        else return ResultData.ok().message("收藏失败");
    }

    @ApiOperation(value = "取消收藏课程")
    @DeleteMapping(value = "/auth/removeCollect/{courseId}")
    public ResultData removeCollect(@ApiParam(value = "课程ID") @PathVariable(value = "courseId") String courseId, HttpServletRequest httpServletRequest) {
        //判断用户登录状态是否正常
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        Boolean addCollect = courseCollectService.removeCollect(courseId, jwtInfo.getId());
        if (addCollect) return ResultData.ok().message("取消成功");
        else return ResultData.ok().message("取消失败");
    }

    @ApiOperation(value = "获取收藏列表")
    @GetMapping(value = "/auth/list")
    public ResultData getCollectList(HttpServletRequest httpServletRequest) {
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        List<CourseCollectReturnVo> courseCollectReturnVoList = courseCollectService.getCourseCollectList(jwtInfo.getId());
        return ResultData.ok().data("items", courseCollectReturnVoList);
    }
}
