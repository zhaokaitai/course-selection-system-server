package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author 秦乾正
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 课程代码
     */
    @TableId
    private String courseCode;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 英文名称
     */
    private String courseEnname;

    /**
     * 开课学院
     */
    private Long collegeId;

    /**
     * 总课时
     */
    private Long totalTime;

    /**
     * 学分
     */
    private Integer credit;

    /**
     * 开课学期
     */
    private String beginTerm;

    /**
     * 课程类别
     */
    private String courseType;

    /**
     * 课程性质
     */
    private String courseCharacter;

    /**
     * 教学模式
     */
    private String teacherModel;

    /**
     * 成绩录入级别
     */
    private String scoreLevel;

    /**
     * 教学班个数
     */
    private Integer classNum;

    /**
     * 可否申请免听(0-Y 1-N)
     */
    private Integer isListing;

    /**
     * 是否统一安排考试(0-Y 1-N)
     */
    private Integer isTotalexam;

    /**
     * 是否统一安排补考（0-Y 1-N)
     */
    private Integer isTotalreexam;

    /**
     * 是否可以补考(0-Y 1-N)
     */
    private Integer isReexam;

    /**
     * 是否实践课(0-Y 1-N)
     */
    private Integer isPractice;

    /**
     * 面向对象
     */
    private String oo;

    /**
     * 周学时
     */
    private Integer weekTime;

    /**
     * 预修课
     */
    private String adviceClass;

    /**
     * 课程简介
     */
    private String courseIntroduction;

    /**
     * 教学大纲
     */
    private String teacheringProgrammer;

    /**
     * 课程归属
     */
    private String courseOwnership;

    /**
     * 教材编号
     */
    private String bookCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}