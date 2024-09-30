package com.heub.selectcourse.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author qqz
 * @date 2024/9/30
 * <p>
 * description
 */
@Data
public class LearningLessonVo {

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
     * 是否自选（0-是 1不是）
     */
    private Integer idOptional;

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

}
