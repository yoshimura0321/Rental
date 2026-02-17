package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.form.UserEntity;

public class CartMapper implements RowMapper<CartEntity> {
	
	@Override
	public CartEntity mapRow(ResultSet rs,int rowNum)throws SQLException{
		
		CartEntity cartEntity = new CartEntity();
		ProductEntity productEntity = new ProductEntity();
		UserEntity userEntity = new UserEntity();
		
		productEntity.setProductId(rs.getInt("product_id"));
		productEntity.setProductName(rs.getString("product_name"));
		productEntity.setProductCategory(rs.getString("product_category"));
		
		userEntity.setUserName(rs.getString("user_name"));
		userEntity.setAddress(rs.getString("address"));
		userEntity.setEmail(rs.getString("email"));
		userEntity.setRentalCount(rs.getInt("rental_count"));
		
		cartEntity.setCartId(rs.getInt("cart_id"));
		cartEntity.setUserId(rs.getInt("user_id"));
		cartEntity.setProductId(rs.getInt("product_id"));
		cartEntity.setStatus(rs.getString("status"));
		cartEntity.setPriority(rs.getInt("priority"));
		cartEntity.setAvailable(rs.getInt("available"));
		
		cartEntity.setProduct(productEntity);
		cartEntity.setUser(userEntity);
		cartEntity.setRentalLimit(rs.getInt("rental_limit"));
		return cartEntity;
	}
}
