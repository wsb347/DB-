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
	
	public ArrayList<Article> SearchArticle(int searchFlag, String keyword) {
		String sql1 = "SELECT * FROM article WHERE ";
		String sql2 = "";
		
		if(searchFlag == 1) {
			sql2 = "title LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 2) {
			sql2 = "SELECT * FROM article WHERE `body` LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 3) {
			sql2 = "SELECT * FROM article WHERE `body` OR title LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 4) {
			sql2 = "nickname LIKE CONCAT_WS(?,'%', '%')";
		}
		return db.getRows(sql1+sql2, new ArticleRowMapper(), searchFlag, keyword);
	}
	
//	public ArrayList<Article> SearchArticleByTitle(String keyword) {
//		String sql = "SELECT * FROM article WHERE title LIKE CONCAT_WS(?,'%', '%')";
//		return db.getRows(sql, new ArticleRowMapper(), keyword);
//	}
//	
//	public ArrayList<Article> SearchArticleByBody(String keyword) {
//		String sql = "SELECT * FROM article WHERE `body` LIKE CONCAT_WS(?,'%', '%')";
//		return db.getRows(sql, new ArticleRowMapper(), keyword);
//	}
//	
//	public ArrayList<Article> SearchArticleByTitleAndBody(String keyword) {
//		String sql = "SELECT * FROM article WHERE `body` OR title LIKE CONCAT_WS(?,'%', '%')";
//		return db.getRows(sql, new ArticleRowMapper(), keyword);
//	}
	
	public int insertReply(int aid, String body, String writer) {
		String sql = "insert into reply set aid = ?, `body` = ?, writer = ?, regDate = NOW()";
		return db.updateQuery(sql, aid, body, writer);
	}
	
	public ArrayList<Reply> getReplyByArticleId(int id) {
		String sql = "select * from reply where aid = ?";
		return db.getRows(sql, new ReplyRowMapper(), id);
	}

	public ArrayList<Article> getSortedArticles(String sortFlag, String sortType) {
		String sql1 = "select * from article order by";
		String sql2 = sortFlag + " " + sortType;
		
		String sql = sql1 + sql2;
		return db.getRows(sql, new ArticleRowMapper());
	}
	
		
	
	
}
