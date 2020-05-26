package com.sl.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.spring.entity.User;
import com.sl.spring.mapper.UserMapper;
import com.sl.spring.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
		@Autowired
		UserMapper mapper;
	
	@Override
	public User findByName(String name) {
		
		return mapper.findByName(name);
	}

	@Override
	public User findById(int id) {
		return mapper.findById(id);
	}

}
