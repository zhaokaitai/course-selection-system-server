package com.heub.selectcourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.Timeset;
import com.heub.selectcourse.model.query.TimeSetQuery;

/**
* @author 秦乾正
* @description 针对表【timeset】的数据库操作Service
* @createDate 2024-09-29 14:27:43
*/
public interface TimesetService extends IService<Timeset> {
	
	Boolean save(TimeSetQuery timesetQuery);
	
	TimeSetQuery getAll();
}
