package com.heub.selectcourse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.exception.BusinessException;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.query.CourseQuery;
import com.heub.selectcourse.model.vo.CourseClassVo;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @Resource
    private TeachingClassService teachingClassService;


    @GetMapping("/page")
    public BaseResponse<List<CourseClassVo>> courseClassList(CourseQuery courseQuery) {

        return  ResultUtils.success(courseService.getCourseClassList(courseQuery));

    }
}
