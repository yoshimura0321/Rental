package jp.ken.rental.form;

import lombok.Data;

@Data
public class CartEntity {
	
	private int cartId;
	
	private int userId;
	
	private int productId;
	
	private String status;
	
	private int priority;
	
	private int available;
	
	private ProductEntity product;
	
	private UserEntity user;
	
	private int rentalLimit;
	
	private int rankCount;
}
