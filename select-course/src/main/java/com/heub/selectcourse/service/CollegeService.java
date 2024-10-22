package com.heub.selectcourse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.College;

/**
* @author 秦乾正
* @description 针对表【college】的数据库操作Service
* @createDate 2024-09-29 14:27:21
*/
public interface CollegeService extends IService<College> {
	
	void deleteAll();
}
