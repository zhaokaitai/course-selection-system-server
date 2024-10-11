package com.heub.selectcourse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.LearningLesson;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.vo.LearningLessonVo;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【learning_lesson】的数据库操作Service
* @createDate 2024-09-26 20:20:30
*/
public interface LearningLessonService extends IService<LearningLesson> {

    List<LearningLessonVo> searchSelfLesson(String studentNumber);

    List<String> searchStudentNumberByLessonId(Long lessonId);
}
