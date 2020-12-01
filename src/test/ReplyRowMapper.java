package test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReplyRowMapper implements RowMapper<Reply>{

	@Override
	public Reply getRow(ResultSet rs) throws SQLException {
		int aid = rs.getInt("aid");
		int id = rs.getInt("id");
		String body = rs.getString("body");
		String writer = rs.getString("writer");
		String regDate = rs.getString("regDate");

		Reply reply = new Reply();
		reply.setParentId(aid);
		reply.setId(id);   
		reply.setBody(body);
		reply.setWriter(writer);
		reply.setRegDate(regDate);

		return reply;
	}

}
