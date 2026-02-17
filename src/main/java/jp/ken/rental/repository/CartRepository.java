package jp.ken.rental.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.infrastructure.mapper.CartMapper;
import jp.ken.rental.infrastructure.mapper.CartRankMapper;
import jp.ken.rental.infrastructure.mapper.CartrentalMapper;

@Repository
public class CartRepository {
	
	private RowMapper<CartEntity> cartMapper = new CartMapper();
	private RowMapper<CartEntity> cartRankMapper=new CartRankMapper();
	private RowMapper<CartEntity> cartrentalMapper = new CartrentalMapper();
	private JdbcTemplate jdbcTemplate;
	
	public CartRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CartEntity> getCartByUserId(int userId)throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE c.user_id = ? AND c.status = 'cart'");
		sb.append(" ORDER BY c.priority");
		String sql = sb.toString();
		
		List<CartEntity> cartList = jdbcTemplate.query(sql,cartMapper,userId);
		
		return cartList;
	}
	
	public List<CartEntity> getRentalsByUserId(int userId) throws Exception {
	    StringBuilder sb = createCommonSQL();
	    // レンタル中
	    sb.append(" WHERE c.user_id = ? AND c.status = 'rental'"); 
	    String sql = sb.toString();

	    List<CartEntity> rentalList = jdbcTemplate.query(sql, cartMapper, userId);
	    return rentalList;
	}
	
	public List<CartEntity> getRentalHistoryByUserId(int userId) throws Exception {
	    StringBuilder sb = createCommonSQL();
	    // 返却済み
	    sb.append(" WHERE c.user_id = ? AND c.status = 'return'"); 
	    String sql = sb.toString();

	    List<CartEntity> historyList = jdbcTemplate.query(sql, cartMapper, userId);
	    return historyList;
	}


	
	public int addCart(CartEntity cartEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO cart (user_id,product_id,status,priority)");
		sb.append(" VALUES (?,?,?,IFNULL((SELECT MAX(priority) FROM cart c WHERE user_id=? GROUP BY c.user_id),0)+1)");
		String sql = sb.toString();
		
		Object[] parameters = { cartEntity.getUserId(),cartEntity.getProductId(),cartEntity.getStatus(),cartEntity.getUserId()};
		
		int num =0;
		num = jdbcTemplate.update(sql,parameters);
		
		return num;
	}
	
	public int deleteCart(CartEntity cartEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM cart");
		sb.append(" WHERE user_id = ? AND product_id = ?");
		String sql = sb.toString();
		
		Object[] parameters = {cartEntity.getUserId(),cartEntity.getProductId()};
		
		int num = 0;
		num = jdbcTemplate.update(sql,parameters);
		
		return num;
	}
	
	//優先度の上下
	public int switchpriority(int cartId1,int priority1,int cartId2,int priority2)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE cart SET priority=? WHERE cart_id=?");
		String sql = sb.toString();
		
		return jdbcTemplate.update(sql,priority2,cartId1)+jdbcTemplate.update(sql,priority1,cartId2);
	}
	
	//優先度の一つ上を探す
	public CartEntity upSearch(int userId,int priority)throws Exception {
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE c.user_id=? AND c.priority<? AND c.status='cart' ORDER BY priority DESC LIMIT 1");
		String sql = sb.toString();
		
		return jdbcTemplate.queryForObject(sql,cartMapper,userId,priority);
	}
	
	//優先度一つ下を探す
	public CartEntity downSearch(int userId,int priority)throws Exception {
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE c.user_id=? AND c.priority>? AND c.status='cart' ORDER BY priority LIMIT 1");
		String sql = sb.toString();
		
		return jdbcTemplate.queryForObject(sql,cartMapper,userId,priority);
	}
	
	private StringBuilder createCommonSQL() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" c.cart_id,c.user_id ,c.product_id, c.status,c.priority, p.product_category, p.product_name");
		sb.append(",u.user_name,u.address,u.email,u.rental_count,l.rental_limit,a.available");
		sb.append(" FROM cart c");
		sb.append(" JOIN items p");
		sb.append(" ON c.product_id = p.product_id");
		sb.append(" JOIN users_profile u");
		sb.append(" ON c.user_id = u.user_id");
		sb.append(" JOIN plan l ON u.plan_name=l.plan_name");
		sb.append(" JOIN (SELECT s.product_id,s.stock_quantity,(s.stock_quantity - IFNULL(r.rental_count,0)) AS available FROM stock s LEFT OUTER JOIN (SELECT product_id,COUNT(*) AS rental_count FROM cart WHERE status='rental' GROUP BY product_id) r ");
		sb.append(" ON s.product_id=r.product_id)a");
		sb.append(" ON a.product_id=c.product_id");
		
		
		return sb;
	}
	
	public List<CartEntity> admincart(int productId)throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE c.product_id=? AND c.status='cart'");
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql,cartMapper,productId);
		
		return list;
	}
	
	public int rental(int userId,int productId)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE cart SET status='rental'");
		sb.append(" WHERE user_id=? AND product_id=? AND status='cart'");
		String sql = sb.toString();
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("UPDATE stock SET rent_count=rent_count+1");
		sb2.append(" WHERE product_id=?");
		String sql2 = sb2.toString();
		
		int num = jdbcTemplate.update(sql,userId,productId)+jdbcTemplate.update(sql2,productId);
		
		return num;
	}
	
	public List<CartEntity> getreturnlist()throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE c.status='rental'");
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql, cartMapper);
		
		return list;
	}
	
	public int doreturn(int userId,int prductId)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE cart SET status='return'");
		sb.append(" WHERE user_id=? AND product_id=?");
		String sql = sb.toString();
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("UPDATE stock SET rent_count=rent_count-1");
		sb2.append(" WHERE product_id=?");
		String sql2 = sb2.toString();
		
		int num = jdbcTemplate.update(sql,userId,prductId)+jdbcTemplate.update(sql2,prductId);
		
		return num;
	}
	
	public List<CartEntity> getDirectorRank()throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT p.product_name,p.creator,COUNT(c.product_id) AS rank_count");
		sb.append(" FROM cart c");
		sb.append(" JOIN items p");
		sb.append(" ON c.product_id = p.product_id AND p.product_category='DVD'");
		sb.append(" WHERE c.status='rental' OR c.status='return'");
		sb.append(" GROUP BY p.creator");
		sb.append(" ORDER BY rank_count DESC LIMIT 5");
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql, cartRankMapper);
		
		return list;
		
	}
	
	public List<CartEntity> getMusicianRank()throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT p.product_name,p.creator,COUNT(c.product_id) AS rank_count");
		sb.append(" FROM cart c");
		sb.append(" JOIN items p");
		sb.append(" ON c.product_id = p.product_id AND p.product_category='CD'");
		sb.append(" WHERE c.status='rental' OR c.status='return'");
		sb.append(" GROUP BY p.creator");
		sb.append(" ORDER BY rank_count DESC LIMIT 5");
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql, cartRankMapper);
		
		return list;
		
	}
	
	
	public List<CartEntity> getProductRank()throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.product_name,p.creator,COUNT(c.user_id) AS rank_count");
		sb.append(" FROM cart c");
		sb.append(" JOIN items p");
		sb.append(" ON c.product_id = p.product_id");
		sb.append(" WHERE c.status='rental' OR c.status='return'");
		sb.append(" GROUP BY p.product_name");
		sb.append(" ORDER BY rank_count DESC LIMIT 5");
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql, cartRankMapper);
		
		return list;
	}
	public int countByStatus(String status) throws Exception {
	    String sql = "SELECT COUNT(*) FROM cart WHERE status = ?";
	    return jdbcTemplate.queryForObject(sql, Integer.class, status);
	}
	
	public List<CartEntity> getRentalusers()throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT");
		sb.append(" t.cart_id,t.user_id ,t.product_id, t.status,t.priority, p.product_category, p.product_name");
		sb.append(",u.user_name,u.address,u.email,u.rental_count,l.rental_limit,t.available");
		sb.append(" FROM (SELECT c.cart_id,c.user_id,c.product_id,c.status,c.priority,RANK() OVER (PARTITION BY c.user_id ORDER BY c.priority) AS ranking,available");
		sb.append(" FROM cart c JOIN (SELECT s.product_id,(s.stock_quantity - IFNULL(r.rental_count,0)) AS available");
		sb.append(" FROM stock s LEFT OUTER JOIN (SELECT product_id,COUNT(*) AS rental_count FROM cart WHERE status='rental' GROUP BY product_id)r");
		sb.append(" ON s.product_id=r.product_id)a");
		sb.append(" ON a.product_id=c.product_id WHERE c.status='cart' ) t");
		sb.append(" JOIN items p");
		sb.append(" ON t.product_id = p.product_id");
		sb.append(" JOIN users_profile u");
		sb.append(" ON t.user_id = u.user_id");
		sb.append(" JOIN plan l ON u.plan_name=l.plan_name");
		sb.append(" WHERE t.ranking=1 ORDER BY l.plan_id DESC,t.cart_id");
		
		String sql = sb.toString();
		
		List<CartEntity> list = jdbcTemplate.query(sql, cartrentalMapper);
		
		return list;
	}

}
