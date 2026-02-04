package jp.ken.rental.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import jp.ken.rental.form.UserEntity;
import jp.ken.rental.infrastructure.mapper.UserRowMapper;

	@Repository
	public class UserRepository {
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
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
			sb.append(" FROM users_table");
		
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
					userEntity.getPlanName(), LocalDate.now()};
					
	
			int numberOfRow = 0;
			numberOfRow = jdbcTemplate.update(sql,parameters);
	
			return numberOfRow;
		}
		
		public int securityregist(UserEntity entity)throws Exception{
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO users (username ,password,enabled) ");
			sb.append(" VALUES (?,?,1)");
			String sql = sb.toString();
			
			Object[] parameters = {entity.getEmail(),"{bcrypt}" + bcpe.encode(entity.getPassword())};
			
			StringBuilder sb2 = new StringBuilder();
			sb2.append("INSERT INTO authorities (username,authority)");
			sb2.append(" VALUES (?,'ROLE_USER')");
			String sql2 = sb2.toString();
			
			int num = jdbcTemplate.update(sql,parameters) * jdbcTemplate.update(sql2,entity.getEmail());
			
			return num;
		}
		
		// userEntityごと渡されて、削除する
		public int deleteUser(int userId) throws Exception{
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM users_table");
			sb.append(" WHERE user_id = ?");
			String sql = sb.toString();

			int num = jdbcTemplate.update(sql, userId);

			return  num;
		}
		
		//会員情報更新ボタン
		public int updateUser(UserEntity userEntity) throws Exception{
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE users_table");
			sb.append(" SET user_name = ?, email = ?, tel = ?, password = ?,");
			sb.append(" birth = ?, address = ?, credit = ?, plan_name = ?");
			sb.append(" WHERE user_id = ?");
			String sql = sb.toString();

			Object[] parameters = { userEntity.getUserName(), userEntity.getEmail(), userEntity.getTel(), bcpe.encode(userEntity.getPassword()),
					userEntity.getBirth(),userEntity.getAddress(), userEntity.getCredit(),
					userEntity.getPlanName(), userEntity.getUserId()};

			int num = jdbcTemplate.update(sql, parameters);
			
			return num;
		}

	}