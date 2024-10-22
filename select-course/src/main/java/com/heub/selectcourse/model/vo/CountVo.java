package com.heub.selectcourse.model.vo;

import com.heub.selectcourse.model.domain.TeachingClass;
import lombok.Data;

import java.util.List;

/**
 * @author 赵开泰
 * @program select-course
 * @date 2024/10/22
 * @description
 **/
@Data
public class CountVo {
	private Long totalCourse;
	private Long totalClass;
	private Integer unCourseClass;
	private List<TeachingClassVo> teachingClasses;
}