package jp.ken.rental.form;

import java.sql.Date;

import lombok.Data;

@Data
public class ProductEntity {
	
	private int productId;
	
	private String productCategory;
	
	private String productName;
	
	private Date arrivaldate;
	
	private Date releasedate;
}
