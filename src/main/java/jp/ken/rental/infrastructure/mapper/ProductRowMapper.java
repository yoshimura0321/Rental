package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<ProductEntity> {
	
	@Override
	public ProductEntity mapRow(ResultSet rs,int rowNum)throws SQLException{
		
		ProductEntity productEntity = new ProductEntity();
		
		
	}

}
