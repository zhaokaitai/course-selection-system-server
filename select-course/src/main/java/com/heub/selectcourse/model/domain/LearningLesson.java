package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName learning_lesson
 */
@TableName(value ="learning_lesson")
@Data
public class LearningLesson implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 教学班id
     */
    private Long classId;

    /**
     * 教工号
     */
    private String teacherId;

    /**
     * 是否自选（0-是 1不是）
     */
    private Integer idOptional;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}