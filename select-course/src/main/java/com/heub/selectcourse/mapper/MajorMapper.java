package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.Major;
import org.apache.ibatis.annotations.Delete;

/**
* @author 秦乾正
* @description 针对表【major】的数据库操作Mapper
* @createDate 2024-09-29 14:27:33
* @Entity generator.domain.Major
*/
public interface MajorMapper extends BaseMapper<Major> {
	
	@Delete("delete from major")
	void deleteAll();
}




