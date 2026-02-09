package jp.ken.rental.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class AdminProductForm implements Serializable{
	
	private String productId;
	
	private String productCategory;
	
	private String productName;
	
	private String arrivalDate;
	
	private String releaseDate;
	
	private String thumbnail;
	
	private String stockQuantity;
	
	private String rentalCount;

}