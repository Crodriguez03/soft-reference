package com.example.softreference.dto;

public class UserDTO {
	
	private final Long id;
	private final String name;
	private final String surname;
	
	public UserDTO(Long id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
}
