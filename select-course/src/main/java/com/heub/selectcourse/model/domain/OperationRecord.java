package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName operation_record
 */
@TableName(value ="operation_record")
@Data
public class OperationRecord implements Serializable {
    /**
     * 选课记录id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学生名称
     */
    private String userName;

    /**
     * 课程id
     */
    private String courseCode;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 选课操作（0-选课 1-退课）
     */
    private Integer courseOperation;

    /**
     * 操作时间
     */
    private Date time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}