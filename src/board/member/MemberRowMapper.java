package board.member;

import java.sql.ResultSet;
import java.sql.SQLException;

import board.RowMapper;

public class MemberRowMapper  implements RowMapper<Member>{

	@Override
	public Member getRow(ResultSet rs) throws SQLException {
		int mid = rs.getInt("mid");
		String id = rs.getString("id");
		String pw = rs.getString("pw");
		String nickname = rs.getString("nickname");
		String regDate = rs.getString("regDate");

		Member member = new Member();
		member.setMid(mid);
		member.setId(id);   
		member.setPw(pw);
		member.setNickname(nickname);
		member.setRegDate(regDate);
		
		return member;
	}

}
