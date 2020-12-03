package board.article;
import java.util.ArrayList;

import board.DBUtil2;
import board.member.Member;
import board.member.MemberRowMapper;

public class ArticleDao {

	private DBUtil2 db = new DBUtil2();
	
	public ArrayList<Article> getArticles() {
		String sql = "select * from article";
		return db.getRows(sql, new ArticleRowMapper());
	}
	
	public int updateArticle(String title, String body, int aid) {
		String sql = "update article set title = ?, `body` = ? where id = ?";
		return db.updateQuery(sql, title, body, aid);
	}
	
	public int deleteArticle(int aid) {
		String sql = "delete from article where id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int insertArticle(String title, String body, String nickname) {
		String sql = "insert into article set title = ?, `body` = ?, nickname = ?, regDate = NOW(), hit = 0, `like` = 0";
		return db.updateQuery(sql, title, body, nickname);
	}
	
	public Article getArticleById(int aid) {
		String sql = "select * from article where id = ?";
		return db.getRow(sql, new ArticleRowMapper(), aid);
	}
	
	public int hitArticle(int aid) {
		String sql = "UPDATE article SET hit = hit+1 WHERE id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int PlusSetLikebyArticle(int aid) {
		String sql = "UPDATE article SET `like` = `like`+1 WHERE id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int MinusLikebyArticle(int aid) {
		String sql = "UPDATE article SET `like` = `like`-1 WHERE id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int insertReply(int aid, String body, String writer) {
		String sql = "insert into reply set aid = ?, `body` = ?, writer = ?, regDate = NOW()";
		return db.updateQuery(sql, aid, body, writer);
	}
	
	public ArrayList<Reply> getReplyByArticleId(int id) {
		String sql = "select * from reply where aid = ?";
		return db.getRows(sql, new ReplyRowMapper(), id);
	}
		
	
	
}
