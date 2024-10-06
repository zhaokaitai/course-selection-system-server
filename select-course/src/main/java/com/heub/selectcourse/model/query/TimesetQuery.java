package com.heub.selectcourse.model.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 秦乾正
 * @TableName timeset
 */
@Data
public class TimesetQuery implements Serializable {


    private Integer numSelect;

    private Date startTime;

    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}