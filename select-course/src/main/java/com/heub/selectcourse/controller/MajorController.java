package com.heub.selectcourse.controller;

import com.heub.selectcourse.model.domain.Major;
import com.heub.selectcourse.service.MajorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/major")
@Slf4j
public class MajorController {
    @Resource
    private MajorService majorService;

    @GetMapping("{id}")
    public Major getMajor(@PathVariable int id) {
        return majorService.getById(id);
    }
}
