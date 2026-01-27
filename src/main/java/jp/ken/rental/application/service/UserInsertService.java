package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.form.UserForm;

@Service
public class UserInsertService {

	private Repository Repository;
	private ModelMapper modelMapper;
	
	public UserInsertService(Repository Repository,ModelMapper modelMapper) {
		this.Repository = Repository;
		this.modelMapper = modelMapper;
	}
	@Transactional(rollbackFor = Exception.class)
	public int registUser(UserForm form) throws Exception{
		UserEntity entity = null;
		
		entity = convert(form);
		
		int resultRow = Repository./*repositoryのメソッド名*/(entity);
		
		return resultRow;
	}
	
	private UserEntity convert(UserForm form) {
		
		String changeFormatDate = form.getBirth().replace("/", "-");
		form.setBirth(changeFormatDate);
		
		UserEntity entity = modelMapper.map(form, UserEntity.class);
		
		return entity;
	}
}
