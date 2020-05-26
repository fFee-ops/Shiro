package com.sl.spring.mapper;

import com.sl.spring.entity.User;

public interface UserMapper {
	User findById(int id);
	User findByName(String name);
}
