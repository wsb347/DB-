package board.article;

import java.sql.ResultSet;
import java.sql.SQLException;

import board.RowMapper;

<<<<<<< HEAD
public class LikeRowMapper implements RowMapper<Like> {

	@Override
	public Like getRow(ResultSet rs) throws SQLException {
		int mid = rs.getInt("mid");
		int aid = rs.getInt("aid");
		String regDate = rs.getString("regDate");

		Like like = new Like();
		like.setMid(mid);
		like.setAid(aid);
		like.setRegDate(regDate);
		
		return like;
=======
public class LikeRowMapper implements RowMapper {

	@Override
	public Object getRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
>>>>>>> 1f590525ed100366c8b0ee705befcf756c679be8
	}

}
