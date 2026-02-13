package jp.ken.rental.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jp.ken.rental.common.annotation.EmailExists;
import jp.ken.rental.common.validator.groups.ValidGroup1;
import jp.ken.rental.common.validator.groups.ValidGroup2;
import jp.ken.rental.common.validator.groups.ValidGroup3;
import lombok.Data;

@Data
public class UserForm implements Serializable {
	
	private String userId;
	
	@NotEmpty(message="氏名は必須入力です",groups=ValidGroup1.class)
	private String userName;
	
	@NotEmpty(message="メールアドレスは必須入力です",groups=ValidGroup1.class)
	@Email(message="メールアドレス形式で入力してください",groups=ValidGroup2.class)
	@EmailExists(message="既に登録されているメールアドレスです",groups=ValidGroup3.class)
	private String email;
	
	@NotEmpty(message="電話番号は必須入力です",groups=ValidGroup1.class)
	@Pattern(regexp = "^[0-9]{10,11}",message="電話番号はハイフンなし、10桁以上11桁以下、半角数字で入力してください",groups=ValidGroup2.class)
	private String tel;
	
	@NotEmpty(message="パスワードは必須入力です",groups=ValidGroup1.class)
	private String password;
	
	@NotEmpty(message="カレンダーから日にちを選択して下さい",groups=ValidGroup1.class)
	//@Pattern(regexp = "^[0-9]{4}/[0-9]{2}/[0-9]{2}$",message="生年月日はYYYY/MM/DDの形式で入力してください",groups=ValidGroup2.class)
	private String birth;
	
	@NotEmpty(message="住所は必須入力です",groups=ValidGroup1.class)
	private String address;
	
	@Pattern(regexp = "^[0-9]{16}",message="クレジットカードは16桁の半角数字で入力してください",groups=ValidGroup2.class)
	private String credit;
	
	@NotEmpty(message="プラン名を選んでください",groups=ValidGroup1.class)
	private String planName;
	
	
	private String rentalCount;
	
	
	private String membershipMonth;
	
	
}