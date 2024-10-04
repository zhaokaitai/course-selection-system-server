package com.heub.selectcourse.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qqz
 * @date 2024/9/24
 * <p>
 * description
 */
@Data
public class ManagerLoginQuery implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String account;

    private String password;
}
