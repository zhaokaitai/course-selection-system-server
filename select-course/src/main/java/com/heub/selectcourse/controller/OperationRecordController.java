package com.heub.selectcourse.controller;

import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.exception.BusinessException;
import com.heub.selectcourse.model.domain.OperationRecord;
import com.heub.selectcourse.model.query.RecordQuery;
import com.heub.selectcourse.service.OperationRecordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/operation-record")
@Slf4j
public class OperationRecordController {
    @Resource
    private OperationRecordService operationRecordService;


    @GetMapping("page")
    public BaseResponse<Page<OperationRecord>> page(RecordQuery recordQuery){
        if(recordQuery == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        OperationRecord operationRecord = new OperationRecord();
        BeanUtils.copyProperties(recordQuery, operationRecord);
        Page<OperationRecord> page = new Page<>(recordQuery.getPageNum(), recordQuery.getPageSize());
        QueryWrapper<OperationRecord> queryWrapper = new QueryWrapper<>(operationRecord);
        Page<OperationRecord> pageResult = operationRecordService.page(page, queryWrapper);

        return ResultUtils.success(pageResult);

    }


}
