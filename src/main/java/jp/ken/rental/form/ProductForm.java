package jp.ken.rental.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductForm implements Serializable{
	
	private String productId;
	
	private String productCategory;
	
	private String productName;
	
	private String arrivalDate;
	
	private String releaseDate;
	
	private String thumbnail;
	
	private String stockQuantity;
	
	private String rentalCount;
	
	private MultipartFile imageFile;
	
	public MultipartFile getImageFile() {
	    return imageFile;
	}
	
	public void setImageFile(MultipartFile imageFile) {
	    this.imageFile = imageFile;
	}
	
}
