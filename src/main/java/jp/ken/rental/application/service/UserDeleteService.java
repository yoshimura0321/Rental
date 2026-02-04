package jp.ken.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.repository.UserRepository;

@Service 
public class UserDeleteService {
	
	private UserRepository userRepository;
	
	public UserDeleteService(UserRepository userRepository) {
	
		this.userRepository = userRepository; 
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteUser(int userId) throws Exception {

	    int rows = userRepository.deleteUser(userId);

	    if (rows == 0) {
	        throw new IllegalArgumentException("削除できませんでした");
	    }
	    return rows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int securitydelete(int userId) throws Exception {

	    int rows = userRepository.securitydelete(userId);

	    if (rows<2) {
	        throw new Exception("削除できませんでした");
	    }
	    return rows;
	}
}