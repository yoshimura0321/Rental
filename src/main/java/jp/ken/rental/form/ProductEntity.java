package jp.ken.rental.form;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductEntity {
	
	private int productId;
	
	private String productCategory;
	
	private String productName;
	
	private Date arrivaldate;
	
	private Date releasedate;
	
	private String thumbnail;
	
	private List<CartEntity> cartList;
}
