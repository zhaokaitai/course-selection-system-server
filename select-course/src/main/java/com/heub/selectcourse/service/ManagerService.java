package com.heub.selectcourse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heub.selectcourse.model.domain.Manager;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 秦乾正
* @description 针对表【manager】的数据库操作Service
* @createDate 2024-09-29 14:24:45
*/
public interface ManagerService extends IService<Manager> {

    Manager managerLogin(String managerNumber, String managerPassword, HttpServletRequest request);

    int managerLogout(HttpServletRequest request);
}
