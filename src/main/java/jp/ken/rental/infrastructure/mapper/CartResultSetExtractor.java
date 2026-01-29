/*
package jp.ken.rental.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class CartResultSetExtractor implements ResultSetExtractor<Entity> {
	RowMapper<T> RowMapper = new RowMapper();
	RowMapper RowMapper = new RowMapper();
	
	@Override
	public List<Entity> extractData(ResultSet rs) throws SQLException, DataAccessException{
		
		List<javax.swing.text.html.parser.Entity> List = new ArrayList<Entity>();
		
		Entity Entity = null;
		int tmpProductId = 0;
		
		while(rs.next()) {
			if(tmpProductId != rs.getInt("product_id")) {
				Entity = RowMapper.mapRow(rs, 0);
				Entity.setList(new ArrayList<Entity>());
				
				List.add(Entity);
				
			}
			
			
		}
		
	}

}
*/