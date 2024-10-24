package com.heub.selectcourse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.Student;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.model.vo.TeachingClassVo;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【teaching_class】的数据库操作Service
* @createDate 2024-09-29 14:03:00
*/
public interface TeachingClassService extends IService<TeachingClass> {

    List<TeachingClass> getTeachingClassList(String courseCode);

    String getCOurseCode(Integer teachingClassId);

    String getTeachingClassName(Integer teachingClassId);

    int reduceTeachingClassCapacity(Integer teachingClassId);

    int addTeachingClassCapacity(Integer teachingClassId);
    
    List<Long> getAllId();
	
	void deleteAll();
    
    List<TeachingClassVo> getAll();
    
    List<Student> getAllStudentByTeachingClass(Long teachingClassId);
    
    List<TeachingClassVo> searchTeachingClass(String search);
}
