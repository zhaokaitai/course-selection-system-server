package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.Course;
import org.apache.ibatis.annotations.Delete;

/**
* @author 秦乾正
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-09-26 19:09:47
* @Entity generator.domain.Course
*/
public interface CourseMapper extends BaseMapper<Course> {
	
	@Delete("delete from course")
	void deleteAll();
}




