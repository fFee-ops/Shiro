package com.sl.spring.service;

import com.sl.spring.entity.User;

public interface UserService {
	User findById(int id);
	User findByName(String name);
}
