package jp.ken.rental.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jp.ken.rental.common.annotation.EmailExists;
import lombok.Data;

@Data
public class UserForm implements Serializable {
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	@EmailExists(message = "email")
	private String email;
	
	@NotEmpty
	private String tel;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String birth;
	
	@NotEmpty
	private String address;
	
	@NotEmpty
	private int credit;
	
	@NotEmpty
	private String planName;
	
	@NotEmpty
	private String rentalCount;
	
	@NotEmpty
	private String membershipMonth;
	
}
