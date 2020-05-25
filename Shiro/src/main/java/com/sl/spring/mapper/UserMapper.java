package com.sl.spring.mapper;

import com.sl.spring.entity.User;

public interface UserMapper {

	User findByName(String name);
}
