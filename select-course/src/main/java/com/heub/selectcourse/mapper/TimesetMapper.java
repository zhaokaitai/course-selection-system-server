package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.Timeset;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 秦乾正
 * @description 针对表【timeset】的数据库操作Mapper
 * @createDate 2024-09-29 14:27:43
 * @Entity generator.domain.Timeset
 */
public interface TimesetMapper extends BaseMapper<Timeset> {
	
	@Select("select * from timeset")
	List<Timeset> getAll();
	
	@Delete("delete from timeset")
	void deleteAll();
}




