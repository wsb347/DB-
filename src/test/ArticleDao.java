package test;
import java.util.ArrayList;

public class ArticleDao {

	private DBUtil2 db = new DBUtil2();
	
	public ArrayList<Article> getArticles() {
		String sql = "select * from article";
		return db.getRows(sql, new ArticleRowMapper());
	}
	
	public int updateArticle(String title, String body, int aid) {
		String sql = "update article set title = ?, body = ? where id = ?";
		return db.updateQuery(sql, title, body, aid);
	}
	
	public int deleteArticle(int aid) {
		String sql = "delete from article where id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int insertArticle(String title, String body, String nickname) {
		String sql = "insert into article set title = ?, body = ?, nickname = ?, regDate = NOW(), hit = 0";
		return db.updateQuery(sql, title, body, nickname);
	}
	
	public Article getArticleById(int aid) {
		String sql = "select * from article where id = ?";
		return db.getRow(sql, new ArticleRowMapper(), aid);
	}
	
	public int insertReply(int aid, String body, String writer) {
		String sql = "insert into reply set aid = ?, body = ?, writer = ?, regDate = NOW()";
		return db.updateQuery(sql, aid, body, writer);
	}
	
	public ArrayList<Reply> getReplyByArticleId(int id) {
		String sql = "select * from reply where aid = ?";
		return db.getRows(sql, new ReplyRowMapper(), id);
	}
	
	public int insertMember(String id, String pw, String nickname) {
		String sql = "insert into member set id = ?, pw = ?, nickname = ?";
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
