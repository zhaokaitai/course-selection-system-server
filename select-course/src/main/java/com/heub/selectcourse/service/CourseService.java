package com.heub.selectcourse.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.query.CourseQuery;
import com.heub.selectcourse.model.vo.CourseClassVo;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【course】的数据库操作Service
* @createDate 2024-09-26 19:09:47
*/
public interface CourseService extends IService<Course> {


    List<CourseClassVo> getCourseClassList(CourseQuery courseQuery);
}
