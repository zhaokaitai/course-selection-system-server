package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName college
 */
@TableName(value ="college")
@Data
public class College implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 学院代码
     */
    private String collegeCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}