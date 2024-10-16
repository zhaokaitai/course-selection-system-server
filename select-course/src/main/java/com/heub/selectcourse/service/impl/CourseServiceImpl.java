package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.CourseMapper;
import com.heub.selectcourse.mapper.TimesetMapper;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.domain.LearningLesson;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.domain.Timeset;
import com.heub.selectcourse.model.query.ChooseCourseQuery;
import com.heub.selectcourse.model.query.CourseQuery;
import com.heub.selectcourse.model.query.DropCourseQuery;
import com.heub.selectcourse.model.vo.CourseClassVo;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.LearningLessonService;
import com.heub.selectcourse.service.OperationRecordService;
import com.heub.selectcourse.service.TeachingClassService;
import com.heub.selectcourse.util.TimeRangeChecker;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private LearningLessonService learningLessonService;

    @Resource
    private OperationRecordService operationRecordService;

    @Resource
    private TimesetMapper timesetMapper;



    @Override
    public List<CourseClassVo> getCourseClassList(CourseQuery courseQuery) {
        List<CourseClassVo> courseClassVos = new ArrayList<>();
        //判断是否在选课时间
        Timeset timeset = timesetMapper.selectById(1);
        //LocalDateTime now = LocalDateTime.now();
        Date startTime = timeset.getStartTime();
        Date endTime = timeset.getEndTime();
        boolean isInRange = TimeRangeChecker.isWithinSelectionTime(startTime, endTime);
        if (!isInRange) {
            System.out.println("当前时间不在选课时间范围内。");
            return courseClassVos;
        }
        //获取课程列表
        Course course = new Course();
        BeanUtils.copyProperties(courseQuery, course);
        QueryWrapper<Course> courseQueryWrapper = getCourseQueryWrapper(courseQuery);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        for (Course c : courses) {
            CourseClassVo courseClassVo = new CourseClassVo();
            courseClassVo.setCourse(c);
            List<TeachingClass> teachingClassList = teachingClassService.getTeachingClassList(c.getCourseCode());
            courseClassVo.setTeachingClassesList(teachingClassList);
            courseClassVos.add(courseClassVo);
        }
        return courseClassVos;

    }

    @Override
    public boolean dropCourse(DropCourseQuery dropCourseQuery) {
        //添加退课记录
        Long classId = dropCourseQuery.getClassId();
        Course course = courseMapper.selectById(teachingClassService.getById(classId));
        operationRecordService.addDelRecord(dropCourseQuery.getStudentNumber(), classId,course);
        //删除课表中的课程信息
        LambdaQueryWrapper<LearningLesson> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(LearningLesson::getClassId, classId).eq(LearningLesson::getStudentNumber, dropCourseQuery.getStudentNumber());
        //课程容量加一
        teachingClassService.addTeachingClassCapacity(Math.toIntExact(classId));
        return learningLessonService.remove(queryWrapper);
    }

    @Override
    public boolean chooseCourse(@RequestBody ChooseCourseQuery chooseCourseQuery) {
        //添加选课记录
        Long classId = chooseCourseQuery.getClassId();
        Course course = courseMapper.selectById(teachingClassService.getById(classId));
        operationRecordService.addCreRecord(chooseCourseQuery.getStudentNumber(), classId,course);
        //添加课程到课表
        LearningLesson learningLesson = new LearningLesson();
        learningLesson.setIdOptional(0);
        BeanUtils.copyProperties(chooseCourseQuery, learningLesson);
        //课程容量减一
        teachingClassService.reduceTeachingClassCapacity(Math.toIntExact(classId));
        return learningLessonService.save(learningLesson);
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




