package com.heub.selectcourse.controller;

import com.heub.selectcourse.service.TimesetService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/timeset")
@Slf4j

public class TimesetController {
    @Resource
    private TimesetService timesetService;

}
