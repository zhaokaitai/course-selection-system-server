package com.heub.selectcourse.model.query;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.heub.selectcourse.common.PageRequest;
import lombok.Data;

import java.util.Date;


/**
 * 
 * @author 秦乾正
 * @TableName course
 */
@Data
public class RecordQuery extends PageRequest  {

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


}