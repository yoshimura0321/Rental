package jp.ken.rental.form;

import lombok.Data;

@Data
public class CartEntity {
	
	private int userId;
	
	private int productId;
	
	private String status;
	
	private ProductEntity product;
}
