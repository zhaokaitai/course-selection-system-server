package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 */
@Data
public class StudentRegisterQuery implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String studentAccount;

    private String studentPassword;

    private String checkPassword;

    private String studentNumber;
}
