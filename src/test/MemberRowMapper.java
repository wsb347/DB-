package test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper  implements RowMapper<Member>{

	@Override
	public Member getRow(ResultSet rs) throws SQLException {
		int mid = rs.getInt("mid");
		String id = rs.getString("id");
		String pw = rs.getString("pw");
		String nickname = rs.getString("nickname");

		Member member = new Member();
		member.setMid(mid);
		member.setId(id);   
		member.setPw(pw);
		member.setNickname(nickname);
		
		return member;
	}

}
