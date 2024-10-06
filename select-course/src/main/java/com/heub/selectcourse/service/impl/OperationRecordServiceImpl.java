package com.heub.selectcourse.service.impl;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.OperationRecordMapper;
import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.domain.LearningLesson;
import com.heub.selectcourse.model.domain.OperationRecord;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author 秦乾正
* @description 针对表【operation_record】的数据库操作Service实现
* @createDate 2024-09-26 20:25:41
*/
@Service
public class OperationRecordServiceImpl extends ServiceImpl<OperationRecordMapper, OperationRecord>
    implements OperationRecordService {

    @Resource
    private OperationRecordMapper operationRecordMapper;
    @Resource
    private StudentService studentService;


    @Override
    public Boolean addCreRecord(String studentNumber, Long classId, Course course) {
        DateTime now = DateTime.now();
        Student student = studentService.getById(studentNumber);
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setStudentNumber(studentNumber);
        operationRecord.setUserName(student.getUserName());
        operationRecord.setCourseCode(course.getCourseCode());
        operationRecord.setCourseName(course.getName());
        operationRecord.setCourseOperation(0);
        operationRecord.setTime(now);
        int insert = operationRecordMapper.insert(operationRecord);
        return insert > 0;
    }

    @Override
    public Boolean addDelRecord(String studentNumber, Long classId, Course course) {

        DateTime now = DateTime.now();
        Student student = studentService.getById(studentNumber);
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setStudentNumber(studentNumber);
        operationRecord.setUserName(student.getUserName());
        operationRecord.setCourseCode(course.getCourseCode());
        operationRecord.setCourseName(course.getName());
        operationRecord.setCourseOperation(1);
        operationRecord.setTime(now);
        int insert = operationRecordMapper.insert(operationRecord);
        return insert > 0;

    }
}




