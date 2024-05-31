package com.example.softreference.service;

import com.example.softreference.dto.UserDTO;

public interface UserService {

	UserDTO findUser(Long userId);

	void insertUser(UserDTO user);

}
