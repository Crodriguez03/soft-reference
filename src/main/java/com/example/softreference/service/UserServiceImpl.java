package com.example.softreference.service;

import org.springframework.stereotype.Service;

import com.example.softreference.dto.UserDTO;
import com.example.softreference.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO findUser(Long userId) {
		return userRepository.findUser(userId);
	}

	@Override
	public void insertUser(UserDTO user) {
		userRepository.insertUser(user);
	}
}
