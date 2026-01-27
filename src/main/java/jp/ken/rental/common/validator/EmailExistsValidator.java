package jp.ken.rental.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.common.annotation.EmailExists;
import jp.ken.rental.form.UserForm;


public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {
	
	private UserSearchService userSearchService;
	
	public EmailExistsValidator(UserSearchService userSearchService) {
		this.userSearchService = userSearchService;
	}
	
	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {
		
		if(value == null || value.isEmpty()) {
			return true;
		}
		
		try {
			UserForm form =new UserForm();
			form.setEmail(value);
			
			UserForm userForm = userSearchService.getUserByEmail(form);
			
			if(userForm != null) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			return true;
		}
	}

}
