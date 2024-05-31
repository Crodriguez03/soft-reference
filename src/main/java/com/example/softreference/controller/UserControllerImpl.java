package com.example.softreference.controller;

import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.softreference.dto.UserDTO;
import com.example.softreference.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserControllerImpl implements UserController {
	
	private final UserService userService;
	
	public UserControllerImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	@GetMapping
	public ResponseEntity<UserDTO> findUser(@RequestParam final Long userId) {
		UserDTO user = userService.findUser(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void insertUser(@RequestBody final UserDTO user) {
		userService.insertUser(user);
	}	
	
	
	
	// Código para probar la sobrecarga de la memoria y ver que
	// se libera cuando el heap se llena sin fallar la app
	// Levantar con -Xmx256m para que libere memoria el recolector
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	@GetMapping("test")
	public void test() {
		LongStream.range(0, 500000).forEach(id -> 
		restTemplate.postForObject("http://localhost:8080/user", generateUser(id), Void.class));
	}
	
	private UserDTO generateUser(Long id) {
		return new UserDTO(id, "name_" + id, "surname_" + id);
	}
}
