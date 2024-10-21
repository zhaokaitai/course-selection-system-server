package com.heub.selectcourse.controller;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.query.TimesetQuery;
import com.heub.selectcourse.service.TimesetService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/timeSet")
@Slf4j
public class TimesetController {
    @Resource
    private TimesetService timesetService;
    @PostMapping()
    public BaseResponse<Boolean> setTime(@RequestBody TimesetQuery timesetQuery) {
        if (timesetQuery == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = timesetService.save(timesetQuery);
        return ResultUtils.success(result);
    }
    
    @GetMapping()
    public BaseResponse<TimesetQuery> getTimeSets() {
        return ResultUtils.success(timesetService.getAll());
    }
}