package com.heub.selectcourse.controller;

import cn.hutool.core.util.StrUtil;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.Manager;
import com.heub.selectcourse.model.query.ManagerLoginQuery;
import com.heub.selectcourse.model.query.ManagerLoginByPhoneQuery;
import com.heub.selectcourse.model.query.ManagerResetPasswordQuery;
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
	
	@PostMapping("/loginByPhone")
	public BaseResponse<Manager> loginByPhone(@RequestBody ManagerLoginByPhoneQuery managerLoginByPhoneQuery, HttpServletRequest request) {
		if (managerLoginByPhoneQuery == null) {
			return null;
		}
		String phone = managerLoginByPhoneQuery.getPhone();
		String smsCode = managerLoginByPhoneQuery.getSmsCode();
		
		if (StrUtil.hasBlank(phone, smsCode)) {
			return null;
		}
		Manager manager = managerService.loginByPhone(phone, smsCode, request);
		return ResultUtils.success(manager);
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
	
	@PostMapping("/resetPassword")
	public BaseResponse<Integer> resetPassword(@RequestBody ManagerResetPasswordQuery managerResetPasswordRequest,
	                                           HttpServletRequest request) {
		if (managerResetPasswordRequest == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		String phone = managerResetPasswordRequest.getPhone();
		String smsCode = managerResetPasswordRequest.getSmsCode();
		String password = managerResetPasswordRequest.getPassword();
		String passwordTwo = managerResetPasswordRequest.getPasswordTwo();
		if (StrUtil.hasBlank(phone, smsCode, password, passwordTwo) || !password.equals(passwordTwo)) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		int result = managerService.resetPassword(phone, smsCode, password, passwordTwo, request);
		return ResultUtils.success(result);
	}
	
	@PostMapping("/changeAvatarUrl")
	public BaseResponse<Integer> changeAvatarUrl(@RequestBody String avatarUrl, HttpServletRequest request) {
		if (avatarUrl == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		int result = managerService.changeAvatarUrl(avatarUrl, request);
		return ResultUtils.success(result);
	}
	
	@PostMapping("/changeManagerName")
	public BaseResponse<Integer> changeManagerName(@RequestBody String managerName, HttpServletRequest request) {
		if (managerName == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}
		
		int result = managerService.changeManagerName(managerName, request);
		return ResultUtils.success(result);
	}
	
	@PostMapping("/changePhone")
	public BaseResponse<Integer> changePhone(@RequestBody ManagerLoginByPhoneQuery managerLoginByPhoneQuery, HttpServletRequest request) {
		if (managerLoginByPhoneQuery == null) {
			return null;
		}
		String phone = managerLoginByPhoneQuery.getPhone();
		String smsCode = managerLoginByPhoneQuery.getSmsCode();
		
		if (StrUtil.hasBlank(phone, smsCode)) {
			return null;
		}
		int result = managerService.changePhone(phone, smsCode, request);
		return ResultUtils.success(result);
	}
}
