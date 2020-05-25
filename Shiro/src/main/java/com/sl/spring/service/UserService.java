package com.sl.spring.service;

import com.sl.spring.entity.User;

public interface UserService {

	User findByName(String name);
}
