package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.College;
import org.apache.ibatis.annotations.Delete;

/**
* @author 秦乾正
* @description 针对表【college】的数据库操作Mapper
* @createDate 2024-09-29 14:27:21
* @Entity generator.domain.College
*/
public interface CollegeMapper extends BaseMapper<College> {
	
	@Delete("delete from college")
	void deleteAll();
}




