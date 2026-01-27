package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.rental.form.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity>{

	@Override
	public UserEntity mapRow(ResultSet rs, int rowNum) throws
	SQLException {
		
		UserEntity userEntity = new UserEntity();
		
		userEntity.setUserId(rs.getInt("user_id"));
		userEntity.setUserName(rs.getString("user_name"));
		userEntity.setEmail(rs.getString("email"));
		userEntity.setTel(rs.getString("tel"));
		userEntity.setPassword(rs.getString("password"));
		userEntity.setBirth(rs.getDate("birth"));
		userEntity.setAddress(rs.getString("address"));
		userEntity.setCredit(rs.getInt("credit"));
		userEntity.setPlanName(rs.getString("plan_name"));
		userEntity.setRentalCount(rs.getInt("rental_count"));
		userEntity.setMembershipMonth(rs.getDate("membership_month"));
		
		return userEntity;

	}
	
}
