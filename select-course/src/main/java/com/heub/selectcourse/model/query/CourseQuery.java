package com.heub.selectcourse.model.query;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.heub.selectcourse.common.PageRequest;
import lombok.Data;



/**
 * 
 * @author 秦乾正
 * @TableName course
 */
@Data
public class CourseQuery extends PageRequest  {
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
     * 开课学院
     */
    private Long collegeId;




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
     * 教学班个数
     */
    private Integer classNum;

    /**
     * 周学时
     */
    private Integer weekTime;

    /**
     * 课程归属
     */
    private String courseOwnership;


}