package com.heub.selectcourse.controller;

import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.vo.LearningLessonVo;
import com.heub.selectcourse.service.LearningLessonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/learning-lesson")
@Slf4j
public class LearningLessonController {
    @Resource
    private LearningLessonService learningLessonService;

    //获取个人课表相关信息
    @GetMapping()
    public BaseResponse<List<LearningLessonVo>> getLearningLesson(String studentNumber) {
        List<LearningLessonVo> result = learningLessonService.searchSelfLesson(studentNumber);
        return ResultUtils.success(result);
    }
}
