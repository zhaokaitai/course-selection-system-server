package com.heub.selectcourse.controller;

import com.heub.selectcourse.model.domain.College;
import com.heub.selectcourse.model.domain.Major;
import com.heub.selectcourse.service.CollegeService;
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
@RequestMapping("/college")
@Slf4j
public class CollegeController {
    @Resource
    private CollegeService collegeService;

    @GetMapping("{id}")
    public College getCollege(@PathVariable int id) {
        return collegeService.getById(id);
    }
}
