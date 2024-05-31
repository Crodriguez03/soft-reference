package com.example.softreference.repository;

import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.softreference.dto.UserDTO;
import com.example.softreference.reference.UserSoftReference;

import jakarta.annotation.PostConstruct;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private final Map<Number, UserSoftReference> map = new ConcurrentHashMap<>();
	private final ReferenceQueue<UserDTO> referenceQueue = new ReferenceQueue<>();
	
	@PostConstruct
	private void init() {
		ExecutorService exec = Executors.newSingleThreadExecutor(r -> {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		);

		exec.execute(() -> {
			UserSoftReference r;
			try {
				// la llamada a remove() deja el hilo esperando hasta que el 
				// recolector de basura elimine una referencia
				while ((r = (UserSoftReference) referenceQueue.remove()) != null) {
					map.remove(r.getUserId());
					log.info("Eliminado usuario con id {} por el recolector de basura", r.getUserId());
				}
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		
		exec.shutdown();
	}

	@Override
	public UserDTO findUser(Long userId) {
		UserSoftReference reference = map.get(userId);
		return reference != null ? reference.get() : null;
	}

	@Override
	public void insertUser(UserDTO user) {
		map.put(user.getId(), new UserSoftReference(user, referenceQueue));
	}
}
