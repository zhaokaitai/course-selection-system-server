package com.heub.selectcourse.controller;

import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.query.ChooseCourseQuery;
import com.heub.selectcourse.model.query.DropCourseQuery;
import com.heub.selectcourse.model.vo.TeachingClassVo;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
	@Resource
	private CourseService courseService;
	
	@GetMapping("{courseCode}")
	private BaseResponse<List<TeachingClass>> selectTeachingClassByCourseCode(@PathVariable String courseCode) {
		return ResultUtils.success(teachingClassService.getTeachingClassList(courseCode));
	}
	
	@GetMapping()
	private BaseResponse<List<TeachingClassVo>> getTeachingClassAll() {
		return ResultUtils.success(teachingClassService.getAll());
	}
	
	@GetMapping("/getAllStudent")
	private BaseResponse<List<Student>> getAllStudentByTeachingClass(@RequestParam("id") Long teachingClassId) {
		if (teachingClassId == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		return ResultUtils.success(teachingClassService.getAllStudentByTeachingClass(teachingClassId));
	}
	
	@PostMapping()
	private BaseResponse<Integer> addStudent(@RequestBody ChooseCourseQuery chooseCourseQuery) {
		if (chooseCourseQuery == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		return ResultUtils.success(courseService.chooseCourse(chooseCourseQuery) ? 1 : 0);
	}
	
	@GetMapping("search")
	private BaseResponse<List<TeachingClassVo>> searchTeachingClass(@RequestParam("search") String search) {
		if (search == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		return ResultUtils.success(teachingClassService.searchTeachingClass(search));
	}
	
	@DeleteMapping()
	private BaseResponse<Integer> deleteStudent(@RequestBody DropCourseQuery dropCourseQuery){
		if (dropCourseQuery == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		return ResultUtils.success(courseService.dropCourse(dropCourseQuery) ? 1 : 0);
	}
}
