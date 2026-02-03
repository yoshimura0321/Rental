package jp.ken.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.repository.UserRepository;

@Service
public class UserUpdateService {
	
	private UserRepository userRepository;
	
	public UserUpdateService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
		
	}
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(UserEntity userEntity) throws Exception{
		
		int rows = userRepository.deleteUser(userEntity);

	    if (rows == 0) {
	        throw new IllegalArgumentException("ユーザーが存在しません");
	    }
	}
}
