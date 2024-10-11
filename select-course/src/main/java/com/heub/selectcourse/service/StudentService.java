package com.heub.selectcourse.service;

import com.heub.selectcourse.excel.studentInfo;
import com.heub.selectcourse.model.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【student】的数据库操作Service
* @createDate 2024-09-26 14:46:05
*/

public interface StudentService extends IService<Student> {

    String studentRegister(String studentNumber, String studentPassword, String checkPassword);

    Student studentLogin(String studentNumber, String studentPassword, HttpServletRequest request);

    Student getSafetyStudent(Student originStudent);

    int studentLogout(HttpServletRequest request);

    List<studentInfo> getStudentInfoList(List<String> studentNumberList);
}
