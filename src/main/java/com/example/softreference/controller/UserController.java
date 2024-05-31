package com.example.softreference.controller;

import org.springframework.http.ResponseEntity;

import com.example.softreference.dto.UserDTO;

public interface UserController {

	ResponseEntity<UserDTO> findUser(Long userId);

	void insertUser(UserDTO user);

	void test();

}
