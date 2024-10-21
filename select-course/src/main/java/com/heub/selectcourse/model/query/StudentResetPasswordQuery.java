package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentResetPasswordQuery implements Serializable
{
    private String phone;

    private String smsCode;

    private String password;

    private String passwordTwo;
}