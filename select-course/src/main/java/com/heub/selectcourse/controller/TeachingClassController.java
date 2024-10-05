package com.heub.selectcourse.controller;

import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/teaching-class")
@Slf4j
public class TeachingClassController {
    @Resource
    private TeachingClassService teachingClassService;

    @GetMapping("{courseCode}")
    private BaseResponse<List<TeachingClass>> selectTeachingClassByCourseCode(@PathVariable String courseCode){
        return ResultUtils.success(teachingClassService.getTeachingClassList(courseCode));
    }
}
