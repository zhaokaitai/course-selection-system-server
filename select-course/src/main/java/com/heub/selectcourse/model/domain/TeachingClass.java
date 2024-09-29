package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author 秦乾正
 * @TableName teaching_class
 */
@TableName(value ="teaching_class")
@Data
public class TeachingClass implements Serializable {
    /**
     * 住建
     */
    @TableId
    private Long id;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 教学班名称
     */
    private String className;

    /**
     * 上课教室
     */
    private String classroom;

    /**
     * 上课时间
     */
    private String classTime;

    /**
     * 上课地点
     */
    private String classPlace;

    /**
     * 已选人数
     */
    private Integer selectedNum;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 教工号
     */
    private String teacherId;

    /**
     * 教材编号
     */
    private String bookCode;

    /**
     * 当前学年
     */
    private String currentTime;

    /**
     * 当前学期
     */
    private String currentTerm;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}