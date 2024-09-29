package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author 秦乾正
 */
@Data
public class StudentRegisterQuery implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String studentNumber;

    private String studentPassword;

    private String checkPassword;

}
