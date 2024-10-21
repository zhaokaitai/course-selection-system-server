package com.heub.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heub.selectcourse.model.domain.Student;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 秦乾正
 * @description 针对表【student】的数据库操作Mapper
 * @createDate 2024-09-26 14:46:05
 * @Entity generator.domain.Student
 */
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from student where phone = #{phone}")
    Student selectByphone(String phone);

    @Update("update student set phone = #{phone}")
    Boolean updatePhoneById(String studentNumber, String phone);
}