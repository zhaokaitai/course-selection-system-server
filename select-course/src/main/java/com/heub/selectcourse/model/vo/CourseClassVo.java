package com.heub.selectcourse.model.vo;

import com.heub.selectcourse.model.domain.Course;
import com.heub.selectcourse.model.domain.TeachingClass;
import lombok.Data;

import java.util.List;

/**
 * @author qqz
 * @date 2024/10/5
 * <p>
 * description
 */
@Data
public class CourseClassVo {

    private Course course;
    private List<TeachingClass> teachingClassesList;
}
