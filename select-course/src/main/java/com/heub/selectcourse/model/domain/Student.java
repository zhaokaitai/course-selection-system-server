package com.heub.selectcourse.model.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author 秦乾正
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 学号
     */
    @TableId
    private String studentNumber;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 照片
     */
    private String avatarUrl;

    /**
     * 姓名拼音
     */
    private String userSpellname;

    /**
     * 英文名称
     */
    private String userEnname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学院名称
     */
    private String college;

    /**
     * 专业名称
     */
    private String major;

    /**
     * 班级名称
     */
    private String studentClass;

    /**
     * 入学日期
     */
    private Date createTime;

    /**
     * 年级
     */
    private String grade;

    /**
     * 培养方式
     */
    private String trainMode;

    /**
     * 学生类别
     */
    private String studentType;

    /**
     * 培养层次
     */
    private String trainLevel;

    /**
     * 所属学院
     */
    private Long collegeId;

    /**
     * 学制
     */
    private Integer educational;

    /**
     * 系名称
     */
    private String depName;

    /**
     * 学分
     */
    private Integer credit;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}