package jp.ken.rental.application.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<UserForm> getAllUser()throws Exception{
		
		List<UserForm> formlist = null;
		List<UserEntity> entitylist = null;
		
		entitylist = userRepository.getAllUser();
		
		formlist = convert2(entitylist);
		
		return formlist;
	}
	
	private UserForm convert(UserEntity entity) {
		UserForm form = modelMapper.map(entity, UserForm.class);
		
		return form;
	}
	
	private List<UserForm> convert2(List<UserEntity> entitylist){
		List<UserForm> formlist = new ArrayList<UserForm>();
		
		for(UserEntity entity : entitylist) {
			UserForm form = convert(entity);
			formlist.add(form);
		}
		return formlist;
	}
}
