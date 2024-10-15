package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.TeachingClass;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【teaching_class】的数据库操作Mapper
* @createDate 2024-09-29 14:03:00
* @Entity generator.domain.TeachingClass
*/
public interface TeachingClassMapper extends BaseMapper<TeachingClass> {
	
	@Select("select id from teaching_class")
	List<Long> getAllId();
}




