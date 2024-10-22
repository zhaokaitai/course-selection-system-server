package com.heub.selectcourse.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 赵开泰
 * @program select-course
 * @date 2024/10/22
 * @description
 **/
@Data
public class TeachingClassVo {
	private Long id;
	private String courseCode;
	private String className;
	private String classroom;
	private String classTime;
	private String classPlace;
	private Integer selectedNum;
	private Integer capacity;
	private String teacherId;
	private String bookCode;
	private String currentTim;
	private String currentTerm;
	private String teacherName;
}