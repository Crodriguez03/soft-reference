package com.example.softreference.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import com.example.softreference.dto.UserDTO;

public class UserSoftReference extends SoftReference<UserDTO> {

	private final Long userId;
	
	public UserSoftReference(UserDTO referent, ReferenceQueue<UserDTO> q) {
		super(referent, q);
		this.userId = referent.getId();
	}

	public Long getUserId() {
		return userId;
	}
}
