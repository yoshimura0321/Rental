package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.form.UserForm;
import jp.ken.rental.repository.UserRepository;

@Service
public class UserInsertService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserInsertService(UserRepository userRepository,ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	@Transactional(rollbackFor = Exception.class)
	public int registUser(UserForm form) throws Exception{
		UserEntity entity = null;
		
		entity = convert(form);
		
		int resultRow = UserRepository.regist(entity);
		
		return resultRow;
	}
	
	private UserEntity convert(UserForm form) {
		
		String changeFormatDate = form.getBirth().replace("/", "-");
		form.setBirth(changeFormatDate);
		
		UserEntity entity = modelMapper.map(form, UserEntity.class);
		
		return entity;
	}
}
