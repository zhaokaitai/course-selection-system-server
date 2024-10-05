package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.CourseMapper;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.query.CourseQuery;
import com.heub.selectcourse.model.vo.CourseClassVo;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 秦乾正
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-09-26 19:09:47
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeachingClassService teachingClassService;


    @Override
    public List<CourseClassVo> getCourseClassList(CourseQuery courseQuery) {
        //获取课程列表
        Course course = new Course();
        BeanUtils.copyProperties(courseQuery, course);
        QueryWrapper<Course> courseQueryWrapper = getCourseQueryWrapper(courseQuery);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        List<CourseClassVo> courseClassVos = new ArrayList<>();
        for (Course c : courses) {
            CourseClassVo courseClassVo = new CourseClassVo();
            courseClassVo.setCourse(c);
            List<TeachingClass> teachingClassList = teachingClassService.getTeachingClassList(c.getCourseCode());
            courseClassVo.setTeachingClassesList(teachingClassList);
            courseClassVos.add(courseClassVo);
        }
        return courseClassVos;

    }


    private static QueryWrapper<Course> getCourseQueryWrapper(CourseQuery courseQuery) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq(courseQuery.getCourseCode()!=null,"course_code", courseQuery.getCourseCode());
        courseQueryWrapper.eq(courseQuery.getName()!=null,"name", courseQuery.getName());
        courseQueryWrapper.eq(courseQuery.getCollegeId()!=null,"college_id", courseQuery.getCollegeId());
        courseQueryWrapper.eq(courseQuery.getCourseType()!=null,"course_type", courseQuery.getCourseType());
        courseQueryWrapper.eq(courseQuery.getCourseCharacter()!=null,"course_character", courseQuery.getCourseCharacter());
        courseQueryWrapper.eq(courseQuery.getTeacherModel()!=null,"teacher_model", courseQuery.getTeacherModel());
        courseQueryWrapper.eq(courseQuery.getClassNum()!=null,"class_num", courseQuery.getClassNum());
        courseQueryWrapper.eq(courseQuery.getWeekTime()!=null,"week_time", courseQuery.getWeekTime());
        courseQueryWrapper.eq(courseQuery.getCourseOwnership()!=null,"course_ownership", courseQuery.getCourseOwnership());
        return courseQueryWrapper;
    }
}




