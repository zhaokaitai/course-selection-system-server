package com.heub.selectcourse.controller;

import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.query.ChooseCourseQuery;
import com.heub.selectcourse.model.query.CourseQuery;
import com.heub.selectcourse.model.query.DropCourseQuery;
import com.heub.selectcourse.model.vo.CourseClassVo;
import com.heub.selectcourse.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @author qqz
 * @date 2024/9/26
 * <p>
 * description 课程接口
 */
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {


    @Resource
    private CourseService courseService;


    @GetMapping("/list")
    public BaseResponse<List<CourseClassVo>> courseClassList(CourseQuery courseQuery) {
        return  ResultUtils.success(courseService.getCourseClassList(courseQuery));
    }

    //选课操作
    @PostMapping("/choose-course")
    public boolean chooseCourse(ChooseCourseQuery chooseCourseQuery) {
        if(chooseCourseQuery == null) {
            return false;
        }
        return courseService.chooseCourse(chooseCourseQuery);

    }

    //退课操作
    @PostMapping("/drop-course")
    public boolean dropCourse(DropCourseQuery dropCourseQuery){
        if(dropCourseQuery == null) {
            return false;
        }
        return courseService.dropCourse(dropCourseQuery);
    }
}
