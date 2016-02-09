package com.jjh.blueberry.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.jjh.blueberry.dao.UserDao;

@Aspect
public class BeforeHomeService {
	@Autowired
	private UserDao userDao;
	
	@Pointcut("execution(* com.jjh.blueberry.service.HomeService.getBoard*(..)) &&" + "args(model,..)")
	private void getSessionUserName(Model model) {}
	
	@Before("getSessionUserName(model)")
	public void before(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String sessionUserName = auth.getName();
		if(!"anonymousUser".equals(sessionUserName)) {
			String name = userDao.getUserNameById(sessionUserName);
			model.addAttribute("userName", name);
		}
	}
}
