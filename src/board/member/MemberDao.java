package board.member;

import java.util.ArrayList;

import board.DBUtil2;

public class MemberDao {
	private DBUtil2 db = new DBUtil2();

	public int insertMember(String id, String pw, String nickname) {
<<<<<<< HEAD
		String sql = "insert into member set id = ?, pw = ?, nickname = ?, regDate = NOW()";
=======
		String sql = "insert into member set id = ?, pw = ?, nickname = ?, checkNo = 0";
>>>>>>> 1f590525ed100366c8b0ee705befcf756c679be8
		return db.updateQuery(sql, id, pw, nickname);
	}
	
	public Member CheckMemberById(String id) {
		String sql = "select * from member where id = ?";
		return db.getRow(sql, new MemberRowMapper(), id);
	}
	
	public Member CheckMemberByNickName(String nickName) {
		String sql = "select * from member where nickname = ?";
		return db.getRow(sql, new MemberRowMapper(), nickName);
	}
	
	public Member GetMemberByIdAndPw(String id, String pw) {
		String sql = "select * from member where id = ? and pw = ?";
		return db.getRow(sql, new MemberRowMapper(), id, pw);
	}
	
	public ArrayList<Member> getReplyByLogin(String id) {
		String sql = "select * from member where id = ?";
		return db.getRows(sql, new MemberRowMapper(), id);
	}
}
