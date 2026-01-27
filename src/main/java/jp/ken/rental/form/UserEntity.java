package jp.ken.rental.form;

import java.sql.Date;

import lombok.Data;

@Data
public class UserEntity {
	
	private int userId;
	
	private String userName;
	
	private String email;
	
	private String tel;
	
	private String password;
	
	private Date birth;
	
	private String address;
	
	private int credit;
	
	private String planName;
	
	private int rentalCount;
	
	private Date membershipMonth;
	
	

}
