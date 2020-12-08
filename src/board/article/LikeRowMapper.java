package board.article;

import java.sql.ResultSet;
import java.sql.SQLException;

import board.RowMapper;

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
	}

}
