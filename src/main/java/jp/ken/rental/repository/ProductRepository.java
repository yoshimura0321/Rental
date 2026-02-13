package jp.ken.rental.repository;



import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.infrastructure.mapper.AdminProductMapper;
//import jp.ken.rental.infrastructure.mapper.ProductResultSetExtractor;
import jp.ken.rental.infrastructure.mapper.ProductRowMapper;

@Repository
public class ProductRepository {
	
	private RowMapper<ProductEntity> productMapper = new ProductRowMapper();
	private RowMapper<ProductEntity> adminProductMapper = new AdminProductMapper();
	//private ResultSetExtractor<List<ProductEntity>> productExtractor = new ProductResultSetExtractor();
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
	
	//カテゴリーだけ検索
	public List<ProductEntity> getProductByCategory(String category) throws Exception {

	    StringBuilder sb = createCommonSQL();
	    sb.append(" WHERE product_category = ?");
	    sb.append(" ORDER BY arrival_date, release_date");

	    String sql = sb.toString();

	    return jdbcTemplate.query(sql, productMapper, category);
	}
	
	//名前＋カテゴリー検索
	public List<ProductEntity> getProductByNameAndCategory(String name, String category) throws Exception {

	    StringBuilder sb = createCommonSQL();
	    sb.append(" WHERE product_name LIKE ?");
	    sb.append(" AND product_category = ?");
	    sb.append(" ORDER BY arrival_date, release_date");

	    String sql = sb.toString();

	    name = name.replace("%", "\\%").replace("_", "\\_");
	    name = "%" + name + "%";

	    return jdbcTemplate.query(sql, productMapper, name, category);
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
		sb.append(" product_id, product_category, product_name, creator, arrival_date, release_date, thumbnail");
		sb.append(" FROM items");
		
		return sb;
	}
	
	public int registitem (ProductEntity productEntity) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO items ( product_category, product_name, creator, arrival_date, release_date, thumbnail)");
		sb.append(" VALUES (?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = { productEntity.getProductCategory(),
								productEntity.getProductName(), 
								productEntity.getCreator(),
								productEntity.getArrivaldate(), 
								productEntity.getReleasedate(),
								productEntity.getThumbnail()};
				
		int numberOfRow = 0;
		numberOfRow = jdbcTemplate.update(sql,parameters);

		return numberOfRow;
	}
	
	public int deleteItem(int productId) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM items");
		sb.append(" WHERE product_id = ?");
		String sql = sb.toString();
		
		int num = jdbcTemplate.update(sql, productId);
		
		return num;
	}
	

	public int updateProduct(ProductEntity productEntity)throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE items");
		sb.append(" SET product_category = ?, product_name = ?, creator = ?,");
		sb.append(" arrival_date = ?, release_date = ?, ");
		sb.append("thumbnail = ? ");
		sb.append(" WHERE product_id = ?");
		String sql = sb.toString();
		
		Object[] parameters = {productEntity.getProductCategory(),
				productEntity.getProductName(),
				productEntity.getCreator(),
			    productEntity.getArrivaldate(),
			    productEntity.getReleasedate(),
			    productEntity.getThumbnail(),
			    productEntity.getProductId()};

		
		int num = jdbcTemplate.update(sql, parameters);
		
		return num;
	}
	public ProductEntity getProductById(int productId) throws Exception {
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE product_id = ?");
	    String sql = sb.toString();

	    return jdbcTemplate.queryForObject(sql, productMapper, productId);
	}
	
	//商品管理サーチ
//	public List<ProductEntity> adminProductSearch()throws Exception{
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT p.product_id, p.product_category,p.product_name,p.creator,p.arrival_date,p.release_date");
//		sb.append(",c.user_id,c.status");
//		sb.append(" FROM items p");
//		sb.append(" LEFT OUTER JOIN (SELECT * FROM cart WHERE status = 'rental') c");
//		sb.append(" ON p.product_id = c.product_id");
//		sb.append(" ORDER BY p.product_id");
//		
//		String sql = sb.toString();
//		List<ProductEntity> productList = jdbcTemplate.query(sql, productExtractor);
//		
//		return productList;
//		
//	}
	
	public List<ProductEntity> adminProductSearch()throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT p.product_id,p.creator, p.product_category,p.product_name,p.arrival_date,p.release_date");
		sb.append(" ,s.stock_quantity, IFNULL(pc.rental_count,0) AS rental_count FROM items p");
		sb.append(" JOIN stock s ON p.product_id = s.product_id");
		sb.append(" LEFT OUTER JOIN (SELECT product_id,COUNT(user_id) AS rental_count FROM cart WHERE status='rental' GROUP BY product_id) pc");
		sb.append(" ON p.product_id = pc.product_id");
		sb.append(" JOIN cart c ON p.product_id = c.product_id");
		sb.append(" AND c.status='cart'");
		sb.append(" ORDER BY p.product_id");
		String sql = sb.toString();
		
		List<ProductEntity> list = jdbcTemplate.query(sql,adminProductMapper);
		
		return list;
	}
	
	public ProductEntity checkrental(int productId)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.product_id, p.product_category,p.product_name,p.creator,p.arrival_date,p.release_date");
		sb.append(" ,s.stock_quantity, COUNT(c.user_id) AS rental_count FROM items p");
		sb.append(" JOIN stock s ON p.product_id = s.product_id");
		sb.append(" LEFT OUTER JOIN cart c");
		sb.append(" ON p.product_id = c.product_id");
		sb.append(" AND c.status = 'rental'");
		sb.append(" WHERE p.product_id = ?");
		sb.append(" GROUP BY p.product_id, p.product_category,p.product_name,p.creator,p.arrival_date,p.release_date");
		
		String sql = sb.toString();
		
		ProductEntity entity = jdbcTemplate.queryForObject(sql, adminProductMapper,productId);
		
		return entity;
	}

	public List<ProductEntity> adminProductSearchByName(String name)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT p.product_id,p.creator, p.product_category,p.product_name,p.arrival_date,p.release_date");
		sb.append(" ,s.stock_quantity, IFNULL(pc.rental_count,0) AS rental_count FROM items p");
		sb.append(" JOIN stock s ON p.product_id = s.product_id");
		sb.append(" LEFT OUTER JOIN (SELECT product_id,COUNT(user_id) AS rental_count FROM cart WHERE status='rental' GROUP BY product_id) pc");
		sb.append(" ON p.product_id = pc.product_id");
		sb.append(" JOIN cart c ON p.product_id = c.product_id");
		sb.append(" AND c.status='cart'");
		sb.append(" WHERE p.product_name LIKE ?");
		sb.append(" ORDER BY p.product_id");
		String sql = sb.toString();
		
		name = name.replace("%", "\\%").replace("_", "\\_");
		name = "%" + name + "%";
		
		List<ProductEntity> productList = jdbcTemplate.query(sql, adminProductMapper,name);
		
		return productList;
	}

}

