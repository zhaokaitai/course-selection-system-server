package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.TeacherMapper;
import com.heub.selectcourse.model.domain.Teacher;
import com.heub.selectcourse.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 秦乾正
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2024-09-29 14:03:40
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService {
	
	@Resource
	private TeacherMapper teacherMapper;
	
	@Override
	public void deleteAll() {
		teacherMapper.deleteAll();
	}
}




