package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.rental.form.ProductEntity;

public class ProductRowMapper implements RowMapper<ProductEntity> {
	
	@Override
	public ProductEntity mapRow(ResultSet rs,int rowNum)throws SQLException{
		
		ProductEntity productEntity = new ProductEntity();
		
		productEntity.setProductId(rs.getInt("product_id"));
		productEntity.setProductCategory(rs.getString("product_category"));
		productEntity.setProductName(rs.getString("product_name"));
		productEntity.setArrivaldate(rs.getDate("arrival_date"));
		productEntity.setReleasedate(rs.getDate("release_date"));
		productEntity.setThumbnail(rs.getString("thumbnail"));
		
//		productEntity.setStockQuantity(rs.getInt("stock_quantity "));
//		productEntity.setRentalCount(rs.getInt("rental_count"));
		
		return productEntity;
	}

}
