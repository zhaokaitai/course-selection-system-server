package com.heub.selectcourse.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.mapper.StudentMapper;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.model.query.StudentLoginQuery;
import com.heub.selectcourse.model.query.StudentRegisterQuery;
import com.heub.selectcourse.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 学生接口
 *
 * @author 秦乾正
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    private StudentService studentService;

    @PostMapping("/register")
    public BaseResponse<String> studentRegister(@RequestBody StudentRegisterQuery studentRegisterRequest) {
        if (studentRegisterRequest == null) {
            return null;
        }
        String studentNumber = studentRegisterRequest.getStudentNumber();
        String studentPassword = studentRegisterRequest.getStudentPassword();
        String checkPassword = studentRegisterRequest.getCheckPassword();

        if (StrUtil.hasBlank(studentNumber,studentPassword, checkPassword)) {
            return null;
        }
        String result = studentService.studentRegister(studentNumber, studentPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<Student> studentLogin(@RequestBody StudentLoginQuery studentLoginRequest, HttpServletRequest request) {
        if (studentLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String studentNumber = studentLoginRequest.getStudentNumber();
        String studentPassword = studentLoginRequest.getStudentPassword();
        if (StrUtil.hasBlank(studentNumber, studentPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Student student = studentService.studentLogin(studentNumber, studentPassword, request);
        return ResultUtils.success(student);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> studentLogout(HttpServletRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int result = studentService.studentLogout(request);
        return ResultUtils.success(result);
    }



}
