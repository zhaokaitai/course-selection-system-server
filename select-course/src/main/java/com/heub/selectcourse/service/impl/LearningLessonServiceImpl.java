package com.heub.selectcourse.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.LearningLessonMapper;
import com.heub.selectcourse.mapper.TeachingClassMapper;
import com.heub.selectcourse.model.domain.LearningLesson;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.vo.LearningLessonVo;
import com.heub.selectcourse.service.LearningLessonService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 秦乾正
 * @description 针对表【learning_lesson】的数据库操作Service实现
 * @createDate 2024-09-26 20:20:30
 */
@Service
public class LearningLessonServiceImpl extends ServiceImpl<LearningLessonMapper, LearningLesson>
		implements LearningLessonService {
	
	@Resource
	private LearningLessonMapper learningLessonMapper;
	
	@Resource
	private TeachingClassMapper teachingClassMapper;
	
	@Override
	public List<LearningLessonVo> searchSelfLesson(String studentNumber) {
		// 1.验证非空
		if (studentNumber.isEmpty()) {
			return null;
		}
		// 2.获取相关信息
		// 2.1获取课表相关信息
		QueryWrapper<LearningLesson> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("student_number", studentNumber);
		List<LearningLesson> learningLessons = learningLessonMapper.selectList(queryWrapper);
		// 2.2获取教学班相关信息
		List<LearningLessonVo> learningLessonVos = new ArrayList<>();
		for (LearningLesson learningLesson : learningLessons) {
			// 加入课表的相关信息
			LearningLessonVo learningLessonVo = new LearningLessonVo();
			BeanUtils.copyProperties(learningLesson, learningLessonVo);
			// 加入教学班的相关信息
			TeachingClass teachingClass = teachingClassMapper.selectById(learningLesson.getClassId());
			BeanUtils.copyProperties(teachingClass, learningLessonVo);
			learningLessonVos.add(learningLessonVo);
		}
		return learningLessonVos;
	}
	
	@Override
	public List<String> searchStudentNumberByLessonId(Long classId) {
		QueryWrapper<LearningLesson> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("class_id", classId);
		List<LearningLesson> learningLessons = learningLessonMapper.selectList(queryWrapper);
		List<String> studentNumbers = new ArrayList<>();
		for (LearningLesson lesson : learningLessons) {
			if (lesson.getStudentNumber() != null) {
				studentNumbers.add(lesson.getStudentNumber());
			}
		}
		return studentNumbers;
	}
	
	@Override
	public List<String> getStudentNumberByTeachingClassId(Long teachingClassId) {
		List<LearningLesson> list = lambdaQuery().eq(LearningLesson::getClassId, teachingClassId).list();
		return list.stream().map(LearningLesson::getStudentNumber).toList();
	}
}




