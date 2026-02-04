package jp.ken.rental.repository;



import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.infrastructure.mapper.ProductRowMapper;

@Repository
public class ProductRepository {
	
	private RowMapper<ProductEntity> productMapper = new ProductRowMapper();
	private JdbcTemplate jdbcTemplate;
	
	public ProductRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public List<ProductEntity> getProductByName(String name)throws Exception{
		
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE product_name");
		sb.append(" LIKE ?");
		sb.append(" ORDER BY arrival_date, release_date");
		String sql = sb.toString();
		
		name = name.replace("%", "\\%").replace("_", "\\_");
		name = "%" + name + "%";
		
		List<ProductEntity> productList = jdbcTemplate.query(sql, productMapper,name);
		
		return productList;
	}
	

	public List<ProductEntity> getProductByArrivalDate() throws Exception {
	    StringBuilder sb = createCommonSQL();
	    
	    sb.append(" ORDER BY arrival_date DESC");
	    sb.append(" LIMIT 5");
	    
	    String sql = sb.toString();
	    
	    // パラメータがないため、query(sql, rowMapper) を使用
	    List<ProductEntity> productList = jdbcTemplate.query(sql, productMapper);
	    
	    return productList;
	}

	
	private StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" product_id, product_category, product_name, arrival_date, release_date");
		sb.append(" FROM items");
		
		return sb;
	}
	
	public int registitem (ProductEntity productEntity) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO items ( product_category, product_name, arrival_date, release_date)");
		sb.append(" VALUES (?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { productEntity.getProductCategory(),
								productEntity.getProductName(), 
								productEntity.getArrivaldate(), 
								productEntity.getReleasedate(), };
				
		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);

		return numberOfRow;
	}
}
