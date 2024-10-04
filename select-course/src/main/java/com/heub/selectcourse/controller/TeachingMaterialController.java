package com.heub.selectcourse.controller;

import com.heub.selectcourse.model.domain.TeachingMaterial;
import com.heub.selectcourse.service.TeachingMaterialService;
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
@RequestMapping("/teaching-material")
@Slf4j
public class TeachingMaterialController {
    @Resource
    private TeachingMaterialService teachingMaterialService;

    @GetMapping("{id}")
    public TeachingMaterial getTeachingMaterial(@PathVariable int id) {
        return teachingMaterialService.getById(id);
    }
}
