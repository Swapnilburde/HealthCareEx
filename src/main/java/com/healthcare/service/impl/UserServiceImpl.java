package com.healthcare.service.impl;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.entity.User;
import com.healthcare.repo.UserRepository;
import com.healthcare.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repo;
	
	public Long saveUser(User user) {
		String pwd=user.getPassword();
		user.setPassword(passwordEncoder.encode(pwd));
		return repo.save(user).getId();
	}

	@Override
	public void removeUser(Long id) {
		repo.delete(getOneUser(id));
	}

	@Override
	public User getOneUser(Long id) {
		Optional<User> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new UsernameNotFoundException(id+ " Not Found");
		}
	}
	
	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<User> opt = findByUsername(username);
		if(!opt.isPresent()) 
			throw new UsernameNotFoundException(username);
		else {
			User user = opt.get();
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(), 
					user.getPassword(), 
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
					);
		}
	}
	@Transactional
	public void updateUserPwd(String pwd, Long userId) {
		String encPwd = passwordEncoder.encode(pwd);
		repo.updateUserPwd(encPwd, userId);
	}
}
