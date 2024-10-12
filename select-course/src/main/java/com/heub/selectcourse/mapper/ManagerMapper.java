package com.heub.selectcourse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.Manager;
import org.apache.ibatis.annotations.Select;

/**
* @author 秦乾正
* @description 针对表【manager】的数据库操作Mapper
* @createDate 2024-09-29 14:24:45
* @Entity generator.domain.Manager
*/
public interface ManagerMapper extends BaseMapper<Manager> {
	
	@Select("select * from manager where phone = #{phone}")
	Manager selectByPhone(String phone);
}




