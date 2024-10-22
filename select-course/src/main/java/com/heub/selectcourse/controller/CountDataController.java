package com.heub.selectcourse.controller;

import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.vo.CountVo;
import com.heub.selectcourse.model.vo.TeachingClassVo;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.TeacherService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 赵开泰
 * @program select-course
 * @date 2024/10/22
 * @description 统计相关controller
 **/

@RestController
@RequestMapping("/count")
@Slf4j
public class CountDataController {
	
	@Resource
	private CourseService courseService;
	@Resource
	private TeachingClassService teachingClassService;
	@Resource
	private TeacherService teacherService;
	
	@GetMapping
	public BaseResponse<CountVo> count() {
		CountVo countVo = new CountVo();
		countVo.setTotalCourse(courseService.count());
		countVo.setTotalClass(teachingClassService.count());
		
		List<TeachingClass> teachingClassList = teachingClassService.lambdaQuery().lt(TeachingClass::getSelectedNum, 20).list();
		countVo.setUnCourseClass(teachingClassList.size());
		countVo.setTeachingClasses(teachingClassList.stream().map(teachingClass -> {
			TeachingClassVo teachingClassVo = new TeachingClassVo();
			teachingClassVo.setId(teachingClass.getId());
			teachingClassVo.setCourseCode(teachingClass.getCourseCode());
			teachingClassVo.setClassName(teachingClass.getClassName());
			teachingClassVo.setClassroom(teachingClass.getClassroom());
			teachingClassVo.setClassTime(teachingClass.getClassTime());
			teachingClassVo.setClassPlace(teachingClass.getClassPlace());
			teachingClassVo.setSelectedNum(teachingClass.getSelectedNum());
			teachingClassVo.setCapacity(teachingClass.getCapacity());
			teachingClassVo.setTeacherId(teachingClass.getTeacherId());
			teachingClassVo.setBookCode(teachingClass.getBookCode());
			teachingClassVo.setCurrentTim(teachingClass.getCurrentTim());
			teachingClassVo.setCurrentTerm(teachingClass.getCurrentTerm());
			teachingClassVo.setTeacherName(teacherService.getById(teachingClass.getTeacherId()).getTeacherName());
			return teachingClassVo;
		}).toList());
		
		return ResultUtils.success(countVo);
	}
	
}