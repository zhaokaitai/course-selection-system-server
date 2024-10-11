package com.heub.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.mapper.TeachingClassMapper;
import com.heub.selectcourse.model.domain.TeachingClass;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 秦乾正
* @description 针对表【teaching_class】的数据库操作Service实现
* @createDate 2024-09-29 14:03:00
*/
@Service
public class TeachingClassServiceImpl extends ServiceImpl<TeachingClassMapper, TeachingClass>
    implements TeachingClassService {

    @Resource
    private TeachingClassMapper teachingClassMapper;

    @Override
    public List<TeachingClass> getTeachingClassList(String courseCode) {

        return teachingClassMapper.selectList(new QueryWrapper<TeachingClass>().eq("course_code", courseCode));
    }

    @Override
    public String getCOurseCode(Integer teachingClassId) {
        TeachingClass teachingClass = teachingClassMapper.selectById(teachingClassId);
        return teachingClass.getCourseCode();
    }

    @Override
    public String getTeachingClassName(Integer teachingClassId) {
        return teachingClassMapper.selectById(teachingClassId).getClassName();
    }
}




