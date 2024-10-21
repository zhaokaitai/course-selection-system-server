package com.heub.selectcourse.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.excel.studentInfo;
import com.heub.selectcourse.exception.BusinessException;
import com.heub.selectcourse.mapper.CollegeMapper;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.service.CollegeService;
import com.heub.selectcourse.service.CourseService;
import com.heub.selectcourse.service.StudentService;
import com.heub.selectcourse.mapper.StudentMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 秦乾正
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2024-09-26 14:46:05
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService{

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CollegeMapper collegeMapper;

    private static final String SALT = "HEUB";

    @Override
    public String studentRegister(String studentNumber, String studentPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(studentNumber, studentPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        if (studentPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(studentNumber);
        if (matcher.find()) {
            return null;
        }
        // 密码和校验密码相同
        if (!studentPassword.equals(checkPassword)) {
            return null;
        }
        // 账户不能重复

        if (studentMapper.selectById(studentNumber) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + studentPassword).getBytes());
        // 3. 插入数据
        Student student = new Student();
        student.setStudentNumber(studentNumber);
        student.setUserPassword(encryptPassword);
        boolean saveResult = this.save(student);
        if (!saveResult) {
            return null;
        }
        return student.getStudentNumber();
    }

    @Override
    public Student studentLogin(String studentNumber, String studentPassword, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(studentNumber, studentPassword)) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(studentPassword);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + studentPassword).getBytes());
        // 查询用户是否存在
        Student student = studentMapper.selectById(studentNumber);
        if (student == null ||!student.getUserPassword().equals(encryptPassword)) {
            return null;
        }
        // 3. 用户脱敏
        Student safetyStudent = getSafetyStudent(student);
        // 4. 记录用户的登录态
        request.getSession().setAttribute("studentLoginState", safetyStudent);
        return safetyStudent;
    }

    /**
     * 用户脱敏
     *
     * @return
     */
    @Override
    public Student getSafetyStudent(Student originStudent) {
        if (originStudent == null) {
            return null;
        }
        Student safetyStudent = new Student();
        safetyStudent.setStudentNumber(originStudent.getStudentNumber());
        safetyStudent.setUserName(originStudent.getUserName());
        safetyStudent.setGender(originStudent.getGender());
        safetyStudent.setAvatarUrl(originStudent.getAvatarUrl());
        safetyStudent.setUserSpellname(originStudent.getUserSpellname());
        safetyStudent.setUserEnname(originStudent.getUserEnname());
        safetyStudent.setPhone(originStudent.getPhone());
        safetyStudent.setEmail(originStudent.getEmail());
        safetyStudent.setCollege(originStudent.getCollege());
        safetyStudent.setMajor(originStudent.getMajor());
        safetyStudent.setStudentClass(originStudent.getStudentClass());
        safetyStudent.setCreateTime(originStudent.getCreateTime());
        safetyStudent.setGrade(originStudent.getGrade());
        safetyStudent.setTrainMode(originStudent.getTrainMode());
        safetyStudent.setStudentType(originStudent.getStudentType());
        safetyStudent.setTrainLevel(originStudent.getTrainLevel());
        safetyStudent.setCollegeId(originStudent.getCollegeId());
        safetyStudent.setEducational(originStudent.getEducational());
        safetyStudent.setDepName(originStudent.getDepName());
        safetyStudent.setCredit(originStudent.getCredit());
        return safetyStudent;
    }


    @Override
    public int studentLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("studentLoginState");
        return 1;
    }

    @Override
    public List<studentInfo> getStudentInfoList(List<String> studentNumberList) {
        if (studentNumberList == null || studentNumberList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Student> students = studentMapper.selectBatchIds(studentNumberList);
        List<studentInfo> studentInfoList = new ArrayList<>();
        for (Student student : students) {
            studentInfo info = new studentInfo();
            info.setStudentNumber(student.getStudentNumber());
            info.setStudentName(student.getUserName());
            info.setGrade(student.getGrade());
            String collegeName = collegeMapper.selectById(student.getCollegeId()).getCollegeName();
            info.setCollege(collegeName);
            info.setMajor(student.getMajor());
            studentInfoList.add(info);
        }
        return studentInfoList;
    }

    @Override
    public int resetPassword(String phone, String smsCode, String password, String passwordTwo, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(phone, smsCode, password, passwordTwo)) {
            return 0;
        }
        // 查询用户是否存在
        Student student = studentMapper.selectByphone(phone);
        System.out.println(
                student
        );
        if (student == null) {return 0;}

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        student.setUserPassword(encryptPassword);
        boolean updateResult = this.updateById(student);
        if (!updateResult) {
            System.out.println(updateResult);
            return 0;
        }
        return 1;
    }

    @Override
    public int changePhone(String phone, String smsCode,String studnetNumber,HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(phone, smsCode)) {
            return 0;
        }

        // 2. 获取用户的登录态
        if (studnetNumber == null) {
            return 0;
        }

        Boolean updateResult = studentMapper.updatePhoneById(studnetNumber, phone);

        if (!updateResult) {
            return 0;
        }

        return 1;
    }

    @Override
    public Student loginByPhone(String phone, String smsCode, HttpServletRequest request)
    {
        // 1. 校验
        if (StrUtil.hasBlank(phone, smsCode)) {
            return null;
        }
        // 查询用户是否存在
        Student student = studentMapper.selectByphone(phone);

        if (student == null) {return null;}

        Student safetyStudent = getSafetyStudent(student);
        // 4. 记录用户的登录态
        request.getSession().setAttribute("studentLoginState", safetyStudent);
        return safetyStudent;

    }
}



