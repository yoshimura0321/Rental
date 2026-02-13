package jp.ken.rental.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jp.ken.rental.common.validator.groups.ValidGroup1;
import lombok.Data;

@Data
public class ProductForm implements Serializable{
	
	
	private String productId;
	
	@NotEmpty(message="カテゴリーは必須入力です",groups=ValidGroup1.class)
	private String productCategory;
	
	@NotEmpty(message="タイトルは必須入力です",groups=ValidGroup1.class)
	private String productName;
	
	@NotEmpty(message="クリエイターは必須入力です",groups=ValidGroup1.class)
	private String creator;
	
	@NotEmpty(message="カレンダーから日にちを選択して下さい",groups=ValidGroup1.class)
	//@Pattern(regexp = "^[0-9]{4}/[0-9]{2}/[0-9]{2}$",message="カレンダーから日にちを選択して下さい",groups=ValidGroup2.class)
	private String arrivalDate;
	
	@NotEmpty(message="カレンダーから日にちを選択して下さい",groups=ValidGroup1.class)
	//@Pattern(regexp = "^[0-9]{4}/[0-9]{2}/[0-9]{2}$",message="カレンダーから日にちを選択して下さい",groups=ValidGroup2.class)
	private String releaseDate;
	
	private String thumbnail;
	
	@NotEmpty(message="在庫は必須入力です",groups=ValidGroup1.class)
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
