package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentLoginByPhoneQuery implements Serializable
{
    private String studentNumber;

    private String phone;

    private String smsCode;
}
