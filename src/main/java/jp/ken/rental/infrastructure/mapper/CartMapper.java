package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.ProductEntity;

public class CartMapper implements RowMapper<CartEntity> {
	
	@Override
	public CartEntity mapRow(ResultSet rs,int rowNum)throws SQLException{
		
		CartEntity cartEntity = new CartEntity();
		ProductEntity productEntity = new ProductEntity();
		
		productEntity.setProductId(rs.getInt("product_id"));
		productEntity.setProductName(rs.getString("product_name"));
		productEntity.setProductCategory(rs.getString("product_category"));
		
		cartEntity.setUserId(rs.getInt("user_id"));
		cartEntity.setProductId(rs.getInt("product_id"));
		cartEntity.setStatus(rs.getString("status"));
		cartEntity.setProduct(productEntity);
		
		return cartEntity;
	}
}
