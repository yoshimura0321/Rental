package jp.ken.rental.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProductForm implements Serializable{
	
	private String productId;
	
	private String productCategory;
	
	private String productName;
	
	private String arrivalDate;
	
	private String releaseDate;
	
	private String thumbnail;
	
	private List<CartForm> cartList;

}
