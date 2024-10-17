package com.heub.selectcourse.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.exception.BusinessException;
import com.heub.selectcourse.mapper.ManagerMapper;
import com.heub.selectcourse.model.domain.Manager;
import com.heub.selectcourse.service.ManagerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 秦乾正
 * @description 针对表【manager】的数据库操作Service实现
 * @createDate 2024-09-29 14:24:45
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager>
		implements ManagerService {
	
	@Resource
	private ManagerMapper managerMapper;
	
	private static final String SALT = "HEUB";
	
	
	@Override
	public Manager managerLogin(String managerNumber, String managerPassword, HttpServletRequest request) {
		// 1. 校验
		if (StrUtil.hasBlank(managerNumber, managerPassword)) {
			return null;
		}
		// 账户不能包含特殊字符
		String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(validPattern).matcher(managerPassword);
		if (matcher.find()) {
			return null;
		}
		// 2. 加密
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + managerPassword).getBytes());
		// 查询用户是否存在
		Manager manager = managerMapper.selectById(managerNumber);
		if (manager == null || !manager.getPassword().equals(encryptPassword)) {
			return null;
		}
		// 3. 用户脱敏
		Manager safetyManager = getSafetyManager(manager);
		// 4. 记录用户的登录态
		request.getSession().setAttribute("managerLoginState", safetyManager);
		return safetyManager;
	}
	
	private Manager getSafetyManager(Manager manager) {
		if (manager == null) {
			return null;
		}
		Manager safetyManager = new Manager();
		safetyManager.setId(manager.getId());
		safetyManager.setManagerName(manager.getManagerName());
		safetyManager.setAccount(manager.getAccount());
		safetyManager.setDepartment(manager.getDepartment());
		safetyManager.setPhone(manager.getPhone());
		safetyManager.setAvatarUrl(manager.getAvatarUrl());
		return safetyManager;
	}
	
	@Override
	public int managerLogout(HttpServletRequest request) {
		request.getSession().removeAttribute("managerLoginState");
		return 1;
	}
	
	@Override
	public String managerRegister(String managerNumber, String managerPassword, String checkPassword) {
		
		if (managerPassword.length() < 8 || checkPassword.length() < 8) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
		}
		// 账户不能包含特殊字符
		String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(validPattern).matcher(managerNumber);
		if (matcher.find()) {
			return null;
		}
		// 密码和校验密码相同
		if (!managerPassword.equals(checkPassword)) {
			return null;
		}
		// 账户不能重复
		
		if (managerMapper.selectById(managerNumber) != null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
		}
		// 2. 加密
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + managerPassword).getBytes());
		// 3. 插入数据
		Manager manager = new Manager();
		manager.setId(Integer.valueOf(managerNumber));
		manager.setAccount(managerNumber);
		manager.setPassword(encryptPassword);
		boolean saveResult = this.save(manager);
		if (!saveResult) {
			return null;
		}
		return manager.getAccount();
	}
	
	@Override
	public Manager loginByPhone(String phone, String smsCode, HttpServletRequest request) {
		// 1. 校验
		if (StrUtil.hasBlank(phone, smsCode)) {
			return null;
		}
		// 查询用户是否存在
		Manager manager = managerMapper.selectByPhone(phone);
		
		if (manager == null) {
			return null;
		}
		// 3. 用户脱敏
		Manager safetyManager = getSafetyManager(manager);
		// 4. 记录用户的登录态
		request.getSession().setAttribute("managerLoginState", safetyManager);
		return safetyManager;
	}
	
	@Override
	public int resetPassword(String phone, String smsCode, String password, String passwordTwo, HttpServletRequest request) {
		// 1. 校验
		if (StrUtil.hasBlank(phone, smsCode, password, passwordTwo)) {
			return 0;
		}
		// 查询用户是否存在
		Manager manager = managerMapper.selectByPhone(phone);
		
		if (manager == null) {
			return 0;
		}
		
		// 2. 加密
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
		
		// 3. 更新密码
		manager.setPassword(encryptPassword);
		boolean updateResult = this.updateById(manager);
		if (!updateResult) {
			return 0;
		}
		
		return 1;
	}
	
	@Override
	public int changeAvatarUrl(String avatarUrl, HttpServletRequest request) {
		// 1. 获取用户的登录态
		Manager managerLoginState = (Manager) request.getSession().getAttribute("managerLoginState");
		
		if (managerLoginState == null) {
			return 0;
		}
		
		Boolean updateResult = managerMapper.updateAvatarUrlById(managerLoginState.getId(), avatarUrl);
		
		if (!updateResult) {
			return 0;
		}
		
		return 1;
	}
	
	@Override
	public int changeManagerName(String managerName, HttpServletRequest request) {
		// 1. 获取用户的登录态
		Manager managerLoginState = (Manager) request.getSession().getAttribute("managerLoginState");
		
		if (managerLoginState == null) {
			return 0;
		}
		
		Boolean updateResult = managerMapper.updateManagerNameById(managerLoginState.getId(), managerName);
		
		if (!updateResult) {
			return 0;
		}
		
		return 1;
	}
	
	@Override
	public int changePhone(String phone, String smsCode, HttpServletRequest request) {
		// 1. 校验
		if (StrUtil.hasBlank(phone, smsCode)) {
			return 0;
		}
		
		// 2. 获取用户的登录态
		Manager managerLoginState = (Manager) request.getSession().getAttribute("managerLoginState");
		
		if (managerLoginState == null) {
			return 0;
		}
		
		Boolean updateResult = managerMapper.updatePhoneById(managerLoginState.getId(), phone);
		
		if (!updateResult) {
			return 0;
		}
		
		return 1;
	}
}




