package com.heub.selectcourse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.TimesetMapper;
import com.heub.selectcourse.model.domain.Timeset;
import com.heub.selectcourse.model.query.TimesetQuery;
import com.heub.selectcourse.service.TimesetService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 秦乾正
 * @description 针对表【timeset】的数据库操作Service实现
 * @createDate 2024-09-29 14:27:43
 */
@Service
public class TimesetServiceImpl extends ServiceImpl<TimesetMapper, Timeset>
		implements TimesetService {
	
	@Resource
	private TimesetMapper timesetMapper;
	
	@Override
	@Transactional
	public Boolean save(TimesetQuery timesetQuery) {
		if (ObjectUtil.hasEmpty(timesetQuery.getFirstDateTime1(), timesetQuery.getSecondDateTime1(), timesetQuery.getFirstDateTime2(),
				timesetQuery.getSecondDateTime2(), timesetQuery.getThirdDateTime2(), timesetQuery.getThirdDateTime1())) {
			return null;
		}
		
		Date firstDateTime1 = timesetQuery.getFirstDateTime1();
		Date secondDateTime1 = timesetQuery.getSecondDateTime1();
		Date firstDateTime2 = timesetQuery.getFirstDateTime2();
		Date secondDateTime2 = timesetQuery.getSecondDateTime2();
		Date thirdDateTime1 = timesetQuery.getThirdDateTime1();
		Date thirdDateTime2 = timesetQuery.getThirdDateTime2();
		
		if (firstDateTime1.equals(firstDateTime2) || thirdDateTime1.equals(thirdDateTime2) || secondDateTime1.equals(secondDateTime2) || firstDateTime2.after(secondDateTime1) || secondDateTime2.after(thirdDateTime1)) {
			return null;
		}
		
		timesetMapper.deleteAll();
		
		Timeset timeset1 = new Timeset();
		timeset1.setNumSelect(1);
		timeset1.setStartTime(timesetQuery.getFirstDateTime1());
		timeset1.setEndTime(timesetQuery.getFirstDateTime2());
		Timeset timeset2 = new Timeset();
		timeset2.setNumSelect(2);
		timeset2.setStartTime(timesetQuery.getSecondDateTime1());
		timeset2.setEndTime(timesetQuery.getSecondDateTime2());
		Timeset timeset3 = new Timeset();
		timeset3.setNumSelect(3);
		timeset3.setStartTime(timesetQuery.getThirdDateTime1());
		timeset3.setEndTime(timesetQuery.getThirdDateTime2());
		
		List<Timeset> timesetList = new ArrayList<>();
		timesetList.add(timeset1);
		timesetList.add(timeset2);
		timesetList.add(timeset3);
		boolean result = saveBatch(timesetList);
		if (!result) {
			return null;
		}
		
		return true;
	}
	
	@Override
	public TimesetQuery getAll() {
		List<Timeset> timesetList = timesetMapper.getAll();
		TimesetQuery timeSetQuery = new TimesetQuery();
		
		timesetList.forEach(timeset -> {
			switch (timeset.getNumSelect()) {
				case 1:
					timeSetQuery.setFirstDateTime1(timeset.getStartTime());
					timeSetQuery.setFirstDateTime2(timeset.getEndTime());
					break;
				case 2:
					timeSetQuery.setSecondDateTime1(timeset.getStartTime());
					timeSetQuery.setSecondDateTime2(timeset.getEndTime());
					break;
				case 3:
					timeSetQuery.setThirdDateTime1(timeset.getStartTime());
					timeSetQuery.setThirdDateTime2(timeset.getEndTime());
					break;
			}
		});
		
		return timeSetQuery;
	}
}




