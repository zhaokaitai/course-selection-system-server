package com.heub.selectcourse.controller;

import cn.hutool.core.util.StrUtil;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.Manager;
import com.heub.selectcourse.model.query.ManagerLoginQuery;
import com.heub.selectcourse.model.query.ManagerRegisterQuery;
import com.heub.selectcourse.service.ManagerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qqz
 * @date 2024/9/29
 * <p>
 * description
 */
@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {
    @Resource
    private ManagerService managerService;

    @PostMapping("/register")
    public BaseResponse<String> managerRegister(@RequestBody ManagerRegisterQuery managerRegisterRequest) {
        if (managerRegisterRequest == null) {
            return null;
        }
        String managerNumber = managerRegisterRequest.getManagerNumber();
        String managerPassword = managerRegisterRequest.getManagerPassword();
        String checkPassword = managerRegisterRequest.getCheckPassword();

        if (StrUtil.hasBlank(managerNumber,managerPassword, checkPassword)) {
            return null;
        }
        String result = managerService.managerRegister(managerNumber, managerPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<Manager> managerLogin(@RequestBody ManagerLoginQuery managerLoginRequest, HttpServletRequest request) {
        if (managerLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String managerNumber = managerLoginRequest.getAccount();
        String managerPassword = managerLoginRequest.getPassword();
        if (StrUtil.hasBlank(managerNumber, managerPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Manager manager = managerService.managerLogin(managerNumber, managerPassword, request);
        return ResultUtils.success(manager);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> managerLogout(HttpServletRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int result = managerService.managerLogout(request);
        return ResultUtils.success(result);
    }
}
