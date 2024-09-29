package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.CourseMapper;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.service.CourseService;
import org.springframework.stereotype.Service;

/**
* @author 秦乾正
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-09-26 19:09:47
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {

}




