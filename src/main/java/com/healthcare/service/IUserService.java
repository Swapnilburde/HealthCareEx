package com.healthcare.service;

import java.util.Optional;

import com.healthcare.entity.User;

public interface IUserService {

	Long saveUser(User user);
	Optional<User> findByUsername(String username);
	public void removeUser(Long id);
	public User getOneUser(Long id);
	void updateUserPwd(String pwd,Long userId);
}
