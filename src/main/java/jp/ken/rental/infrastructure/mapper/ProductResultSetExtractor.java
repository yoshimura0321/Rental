package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.ProductEntity;

public class ProductResultSetExtractor implements ResultSetExtractor<List<ProductEntity>> {
	CartMapper cartMapper = new CartMapper();
	ProductRowMapper productMapper = new ProductRowMapper();
	
	@Override
	public List<ProductEntity> extractData(ResultSet rs)throws SQLException{
		
		List<ProductEntity> productList = new ArrayList<ProductEntity>();
		ProductEntity productEntity = null;
		int tmpId=0;
		
		while(rs.next()) {
			if(tmpId != rs.getInt("product_id")) {
				productEntity = productMapper.mapRow(rs, 0);
				productEntity.setCartList(new ArrayList<CartEntity>());
				productList.add(productEntity);
			}
			CartEntity cartEntity = cartMapper.mapRow(rs, 0);
			productEntity.getCartList().add(cartEntity);
			
			tmpId = rs.getInt("product_id");
		}
		
		return productList;
	}
	
}
