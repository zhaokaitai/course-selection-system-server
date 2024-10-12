package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 赵开泰
 * @program select-course
 * @date 2024/10/12
 * @description
 **/

@Data
public class ManagerResetPasswordQuery implements Serializable {
	private static final long serialVersionUID = 3191241716373120793L;
	
	private String phone;
	
	private String smsCode;
	
	private String password;
	
	private String passwordTwo;
	
}