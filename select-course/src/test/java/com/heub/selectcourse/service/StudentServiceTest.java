package com.heub.selectcourse.service;

import com.heub.selectcourse.model.domain.Student;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qqz
 * @date 2024/9/26
 * <p>
 * description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    public void test() {
        Student student = new Student();
        student.setStudentNumber("123");
        student.setUserPassword("12345678");
        student.setUserName("abc");
        System.out.println(studentService);
        boolean result = studentService.save(student);
        System.out.println(student.getStudentNumber());
    }
}