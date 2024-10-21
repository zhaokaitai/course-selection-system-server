package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.exception.BusinessException;
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
import com.heub.selectcourse.model.vo.LearningLessonVo;
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
import java.util.stream.Collectors;

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
        System.out.println(courseClassVos);
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
        //先从课表中获取学生的选课记录的课程id集合中的时间集合
        List<String> times = learningLessonService.searchSelfLesson(chooseCourseQuery.getStudentNumber())
                .stream().map(LearningLessonVo::getClassTime)
                .toList();
        Long classId = chooseCourseQuery.getClassId();
        TeachingClass teachingClass = teachingClassService.getById(classId);
        String classTime = teachingClass.getClassTime();
        Course course = courseMapper.selectById(teachingClass);
        //查询是否冲突
        for (String time : times) {
            if (isTimeConflict(classTime, time)) {
                return false;
            }
        }
        //添加选课记录
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

    private static boolean isTimeConflict(String time1, String time2) {
        String[] parts1 = time1.split("/");
        String[] parts2 = time2.split("/");

        String day1 = parts1[0];
        String day2 = parts2[0];

        if (!day1.equals(day2)) {
            return false;
        }

        // 解析节次范围
        String[] range1 = parts1[1].split("-");
        String[] range2 = parts2[1].split("-");
        int start1 = Integer.parseInt(range1[0]);
        int end1 = Integer.parseInt(range1[1]);
        int start2 = Integer.parseInt(range2[0]);
        int end2 = Integer.parseInt(range2[1]);

        if (start1 <= end2 && end1 >= start2) {
            // 节次范围有冲突，再检查周数范围
            String[] weekRange1 = parts1[2].split("-");
            String[] weekRange2 = parts2[2].split("-");
            int startWeek1 = Integer.parseInt(weekRange1[0]);
            int endWeek1 = Integer.parseInt(weekRange1[1]);
            int startWeek2 = Integer.parseInt(weekRange2[0]);
            int endWeek2 = Integer.parseInt(weekRange2[1]);

            return isWeekRangeOverlap(startWeek1, endWeek1, startWeek2, endWeek2);
        } else {
            return false;
        }
    }

    private static boolean isWeekRangeOverlap(int startWeek1, int endWeek1, int startWeek2, int endWeek2) {
        return!(endWeek1 < startWeek2 || startWeek1 > endWeek2);
    }

}