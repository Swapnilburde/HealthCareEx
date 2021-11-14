package com.healthcare.runner;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.healthcare.constants.UserRoles;
import com.healthcare.entity.User;
import com.healthcare.service.IUserService;
import com.healthcare.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {

	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String username;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	public void run(String... args) throws Exception {
		Optional<User> opt = userService.findByUsername(username);
		if(!opt.isPresent()) {
			createAdmin();
		}else {
			userService.removeUser(opt.get().getId());
			createAdmin();
		}
	}	
	
	private void createAdmin() {
		User user = new User();
		String pwd=userUtil.genPwd();
		user.setDisplayName(displayName);
		user.setUsername(username);
		user.setPassword(pwd);
		System.out.println("Admin username :"+username);
		System.out.println("Admin password :"+pwd);
		user.setRole(UserRoles.ADMIN.name());
		userService.saveUser(user);
		//TODO : EMAIL SERVICE send pwd
	}

}