package com.heub.selectcourse.controller;

import com.heub.selectcourse.model.domain.Teacher;
import com.heub.selectcourse.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Resource
    private TeacherService teacherService;


    @GetMapping("{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId) {
        return teacherService.getById(teacherId);
    }

}
