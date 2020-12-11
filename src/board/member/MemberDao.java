package board.member;

import java.util.ArrayList;

import board.DBUtil2;

public class MemberDao {
	private DBUtil2 db = new DBUtil2();

	public int insertMember(String id, String pw, String nickname) {
		String sql = "insert into member set id = ?, pw = ?, nickname = ?, checkNo = 0, regDate = NOW()";
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
	
	public Member GetMemberByPw(String id) {
		String sql = "SELECT * FROM `member` WHERE id = ?";
		return db.getRow(sql, new MemberRowMapper(), id);
	}
	
	public int UpdateMemberinfo(int flag, String keyword, String loginId) {
		String sql1 = "UPDATE `member`";
		String sql2 = "";
		
		if(flag == 1) {
			sql2 = "SET pw = ? WHERE id = ?";
		} else if(flag == 2) {
			sql2 = "SET nickname = ? WHERE id = ?";
		}
		
		String sql = sql1 + sql2;
		return db.updateQuery(sql, keyword, loginId);
	}
	
<<<<<<< HEAD
=======
	
>>>>>>> d05bb1c832e66f729624a631bc8b151a1c0a0f89
}
