package jp.ken.rental.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.infrastructure.mapper.UserRowMapper;

	@Repository
	public class UserRepository {
	
		private RowMapper<UserEntity> userMapper = new UserRowMapper();
		private JdbcTemplate jdbcTemplate;
	
		public UserRepository(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
	
		public List<UserEntity>getUserAllList() throws Exception{
			
			StringBuilder sb = createCommonSQL();
			sb.append(" ORDER BY user_id");	
			String sql = sb.toString();
		
			List<UserEntity> userList = jdbcTemplate.query(sql, userMapper);
		
			return userList;
		}
	
		public UserEntity getUserByEmail(String email) throws Exception{
			StringBuilder sb = createCommonSQL();
			sb.append(" WHERE email = ?");
			String sql = sb.toString();
		
			UserEntity userEntity = jdbcTemplate.queryForObject(sql, userMapper,email);
			
					return  userEntity;
		}
	
		private StringBuilder createCommonSQL() {
		
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT");
			sb.append(" user_id, user_name, email, tel, password, birth,");
			sb.append(" address, credit, plan_name, rental_count, membership_month");
			sb.append(" FROM user");
		
			return sb;
		}
		public int regist (UserEntity userEntity) throws Exception{
	
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO users_table ( user_name, email, tel, password, birth,");
			sb.append(" address, credit, plan_name, membership_month)");
					
			
			sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			String sql = sb.toString();
			
			Object[] parameters = { userEntity.getUserName(),
					userEntity.getEmail(), userEntity.getTel(), userEntity.getPassword(),
					userEntity.getBirth(),userEntity.getAddress(), userEntity.getCredit(), 
					userEntity.getPlanName(), "2000-12-12"};
					
	
			int numberOfRow = 0;
			numberOfRow = jdbcTemplate.update(sql,parameters);
	
			return numberOfRow;
		}
	}