package jp.ken.rental.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.infrastructure.mapper.CartMapper;

@Repository
public class CartRepository {
	
	private RowMapper<CartEntity> cartMapper = new CartMapper();
	private JdbcTemplate jdbcTemplate;
	
	public CartRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CartEntity> getCartByUserId(int userId)throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE user_id = ?");
		String sql = sb.toString();
		
		List<CartEntity> cartList = jdbcTemplate.query(sql,cartMapper,userId);
		
		return cartList;
	}
	
	public int addCart(CartEntity cartEntity)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO cart");
		sb.append(" VALUES (?,?,?)");
		String sql = sb.toString();
		
		Object[] parameters = { cartEntity.getUserId(),cartEntity.getProductId(),cartEntity.getStatus()};
		
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
	
	private StringBuilder createCommonSQL() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" c.user_id ,c.product_id, c.status, p.product_category, p.product_name");
		sb.append(" FROM cart c");
		sb.append(" JOIN items p");
		sb.append(" ON c.product_id = p.product_id");
		
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
		sb.append(" WHERE user_id=? AND product_id=?");
		String sql = sb.toString();
		
		int num = jdbcTemplate.update(sql,userId,productId);
		
		return num;
	}
}
