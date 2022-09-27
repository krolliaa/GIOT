package com.kk.feign_api.client;

import com.kk.feign_api.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu")
public interface EduClient {
    @GetMapping(value = "/api/edu/course/inner/get-course-dto/{courseId}")
    public abstract CourseDto getCourseDtoByCourseId(@PathVariable(value = "courseId") String courseId);
}
