package com.heub.selectcourse.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.TeacherMapper;
import com.heub.selectcourse.mapper.TeachingClassMapper;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.vo.TeachingClassVo;
import com.heub.selectcourse.service.LearningLessonService;
import com.heub.selectcourse.service.StudentService;
import com.heub.selectcourse.service.TeacherService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 秦乾正
 * @description 针对表【teaching_class】的数据库操作Service实现
 * @createDate 2024-09-29 14:03:00
 */
@Service
public class TeachingClassServiceImpl extends ServiceImpl<TeachingClassMapper, TeachingClass>
		implements TeachingClassService {
	
	@Resource
	private TeachingClassMapper teachingClassMapper;
	@Resource
	private LearningLessonService learningLessonService;
	@Resource
	private StudentService studentService;
	@Resource
	private TeacherService teacherService;
	
	@Override
	public List<TeachingClass> getTeachingClassList(String courseCode) {
		
		return teachingClassMapper.selectList(new QueryWrapper<TeachingClass>().eq("course_code", courseCode));
	}
	
	@Override
	public String getCOurseCode(Integer teachingClassId) {
		TeachingClass teachingClass = teachingClassMapper.selectById(teachingClassId);
		return teachingClass.getCourseCode();
	}
	
	@Override
	public String getTeachingClassName(Integer teachingClassId) {
		return teachingClassMapper.selectById(teachingClassId).getClassName();
	}
	
	@Override
	public int reduceTeachingClassCapacity(Integer teachingClassId) {
		TeachingClass teachingClass = teachingClassMapper.selectById(teachingClassId);
		teachingClass.setCapacity(teachingClass.getCapacity() - 1);
		return teachingClassMapper.updateById(teachingClass);
	}
	
	@Override
	public int addTeachingClassCapacity(Integer teachingClassId) {
		TeachingClass teachingClass = teachingClassMapper.selectById(teachingClassId);
		teachingClass.setCapacity(teachingClass.getCapacity() + 1);
		return teachingClassMapper.updateById(teachingClass);
	}
	
	@Override
	public List<Long> getAllId() {
		List<Long> ids = teachingClassMapper.getAllId();
		if (ids != null && !ids.isEmpty()) {
			return ids;
		}
		return CollUtil.newArrayList();
	}
	
	@Override
	public void deleteAll() {
		teachingClassMapper.deleteAll();
	}
	
	@Override
	public List<TeachingClassVo> getAll() {
		List<TeachingClass> teachingClassList = teachingClassMapper.getAll();
		List<TeachingClassVo> teachingClassVoList = new ArrayList<>();
		teachingClassList.forEach(teachingClass -> {
			TeachingClassVo teachingClassVo = new TeachingClassVo();
			BeanUtil.copyProperties(teachingClass, teachingClassVo);
			teachingClassVo.setTeacherName(teacherService.getById(teachingClass.getTeacherId()).getTeacherName());
			teachingClassVoList.add(teachingClassVo);
		});
		
		return teachingClassVoList;
	}
	
	@Override
	public List<Student> getAllStudentByTeachingClass(Long teachingClassId) {
		List<String> studentNumberList = learningLessonService.getStudentNumberByTeachingClassId(teachingClassId);
		if (studentNumberList == null || studentNumberList.isEmpty()) {
			return CollUtil.newArrayList();
		}
		
		return studentService.lambdaQuery().in(Student::getStudentNumber, studentNumberList).list();
	}
	
	@Override
	public List<TeachingClassVo> searchTeachingClass(String search) {
		List<TeachingClass> teachingClassList = teachingClassMapper.getTeachingClassByName(search);
		List<TeachingClassVo> teachingClassVoList = new ArrayList<>();
		teachingClassList.forEach(teachingClass -> {
			TeachingClassVo teachingClassVo = new TeachingClassVo();
			BeanUtil.copyProperties(teachingClass, teachingClassVo);
			teachingClassVo.setTeacherName(teacherService.getById(teachingClass.getTeacherId()).getTeacherName());
			teachingClassVoList.add(teachingClassVo);
		});
		
		return teachingClassVoList;
	}
}