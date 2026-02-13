package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.ProductEntity;

public class CartRankMapper implements RowMapper<CartEntity> {
	
	@Override
	public CartEntity mapRow(ResultSet rs,int rowNum)throws SQLException{
		CartEntity centity = new CartEntity();
		ProductEntity pentity = new ProductEntity();
		
		pentity.setCreator(rs.getString("creator"));
		pentity.setProductName(rs.getString("product_name"));
		
		centity.setRankCount(rs.getInt("rank_count"));
		centity.setProduct(pentity);
		
		return centity;
	}
}
