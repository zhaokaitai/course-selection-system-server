package com.heub.selectcourse.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author 秦乾正
 * @TableName teaching_material
 */
@TableName(value ="teaching_material")
@Data
public class TeachingMaterial implements Serializable {
    /**
     * 教材编号
     */
    @TableId
    private String bookCode;

    /**
     * 教材名称
     */
    private String bookName;

    /**
     * 教材作者
     */
    private String bookAuthor;

    /**
     * 出版社
     */
    private String press;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 出版日期
     */
    private Date publicationDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}