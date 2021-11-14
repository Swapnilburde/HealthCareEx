package com.healthcare.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	public String genPwd() {
		String pwd= UUID.randomUUID()
				.toString()
				.replace("-","")
				.substring(0, 8);
		return "1111";
	}
}
