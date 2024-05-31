package com.example.softreference.repository;

import com.example.softreference.dto.UserDTO;

public interface UserRepository {

	UserDTO findUser(Long userId);

	void insertUser(UserDTO user);

}
