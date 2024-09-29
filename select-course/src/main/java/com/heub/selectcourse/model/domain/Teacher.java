package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author 秦乾正
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 教工号
     */
    @TableId
    private String teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 姓名拼音
     */
    private String teacherSpellname;

    /**
     * 英文名称
     */
    private String teacherEnname;

    /**
     * 性别（0-女  1-男）
     */
    private Integer gender;

    /**
     * 所在单位
     */
    private String unit;

    /**
     * 最高学历
     */
    private String educationBackgroude;

    /**
     * 参加工作时间
     */
    private Integer workTime;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 研究方向
     */
    private String researchMethods;

    /**
     * 科室名称
     */
    private String department;

    /**
     * 职称
     */
    private String position;

    /**
     * 教师简介
     */
    private String teacherIntroduction;

    /**
     * 照片
     */
    private String avatarUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}