package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.form.UserForm;
import jp.ken.rental.repository.UserRepository;

@Service
public class UserSearchService {
	
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserSearchService(UserRepository userRepository,ModelMapper modelMapper) {
		this.userRepository=userRepository;
		this.modelMapper=modelMapper;
	}
	
	public UserForm getUserByEmail(UserForm form)throws Exception{
		
		UserEntity userEntity = null;
		UserForm userForm = null;
		String email = form.getEmail();
		
		userEntity = userRepository.getUserByEmail(email);
		
		userForm = convert(userEntity);
		
		return userForm;
	}
	
	private UserForm convert(UserEntity entity) {
		UserForm form = modelMapper.map(entity, UserForm.class);
		
		return form;
	}
}
