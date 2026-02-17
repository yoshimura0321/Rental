package jp.ken.rental.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartForm implements Serializable {
	
	private String cartId;
	
	private String userId;
	
	private String productId;
	
	private String status;
	
	private String priority;
	
	private Integer available;
	
	private ProductForm product;
	
	private UserForm user;
	
	private String rentalLimit;
	
	private String rankCount;
}
