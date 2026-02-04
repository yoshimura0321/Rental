package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.form.UserForm;
import jp.ken.rental.repository.UserRepository;

@Service
public class UserUpdateService {
	
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserUpdateService(UserRepository userRepository,ModelMapper modelMapper) {
		
		this.userRepository = userRepository;
		this.modelMapper=modelMapper;
		
	}
	@Transactional(rollbackFor = Exception.class)
	public int updateUser(UserForm userForm) throws Exception{
		
		UserEntity userEntity = convert(userForm);
		int rows = userRepository.updateUser(userEntity);

	    if (rows == 0) {
	        throw new IllegalArgumentException("更新できませんでした");
	    }
	    return rows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int securityupdate(UserForm userForm) throws Exception{
		
		UserEntity userEntity = convert(userForm);
		int rows = userRepository.securityupdate(userEntity);

	    if (rows<2) {
	        throw new Exception("更新できませんでした");
	    }
	    return rows;
	}
	
	private UserEntity convert(UserForm userForm) {
		String changeFormatDate = userForm.getBirth().replace("/", "-");
		userForm.setBirth(changeFormatDate);
		UserEntity entity = modelMapper.map(userForm, UserEntity.class);
		
		return entity;
	}
}
