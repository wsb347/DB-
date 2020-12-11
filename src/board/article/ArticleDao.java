package board.article;

import java.util.ArrayList;

import board.DBUtil2;

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

	public Article CheckIdArticle(int aid) {
		String sql = "SELECT nickname FROM article WHERE id = ?";
		return db.getRow(sql, new ArticleRowMapper(), aid);
	}

	public int hitArticle(int aid) {
		String sql = "UPDATE article SET hit = hit+1 WHERE id = ?";
		return db.updateQuery(sql, aid);
	}
	
	public int PlusSetLikebyArticle(int aid, int mid) {
		String sql = "UPDATE article a INNER JOIN `like` l ON a.id = l.aid SET a.`like` = a.`like` + 1 WHERE aid = ? AND `mid` = ?";
		return db.updateQuery(sql, aid, mid);
	}
	

	public int MinusLikebyArticle(int aid, int mid) {
		String sql = "UPDATE article a INNER JOIN `like` l ON a.id = l.aid SET a.`like` = a.`like` - 1 WHERE aid = ? AND `mid` = ?";
		return db.updateQuery(sql, aid, mid);
	}


	public ArrayList<Article> SearchArticle(int searchFlag, String keyword) {
		String sql1 = "SELECT * FROM article WHERE ";
		String sql2 = "";
		
		if(searchFlag == 1) {
			sql2 = "title LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 2) {
			sql2 = "`body` LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 3) {
			sql2 = "`body` OR title LIKE CONCAT_WS(?,'%', '%')";
		} else if(searchFlag == 4) {
			sql2 = "nickname LIKE CONCAT_WS(?,'%', '%')";
		}
		
		String sql = sql1 + sql2;
		return db.getRows(sql, new ArticleRowMapper(), keyword);
	}


	public int insertReply(int aid, String body, String writer) {
		String sql = "insert into reply set aid = ?, `body` = ?, writer = ?, regDate = NOW()";
		return db.updateQuery(sql, aid, body, writer);
	}

	public ArrayList<Reply> getReplyByArticleId(int id) {
		String sql = "select * from reply where aid = ?";
		return db.getRows(sql, new ReplyRowMapper(), id);
	}

	public ArrayList<Article> getSortedArticles(String sortFlag, String sortType) {
		String sql1 = "select * from article order by ";
		String sql2 = sortFlag + " " + sortType;

		String sql = sql1 + sql2;
		return db.getRows(sql, new ArticleRowMapper());
	}


	public Like getLike(int aid, int mid) {

		String sql = "select * from `like` where aid = ? and `mid` = ?";
		return db.getRow(sql, new LikeRowMapper(), aid, mid);
	}

	public void deleteLike(int aid, int mid) {
		String sql = "delete from `like` where aid = ? and `mid` = ?";
		db.updateQuery(sql, aid, mid);
	}

	public void insertLike(int aid, int mid) {
		String sql = "insert into `like` set aid = ?, `mid` = ?, regDate = NOW()";
		db.updateQuery(sql, aid, mid);
	}

	public boolean isExistLike(int mid, String aid) {
		String sql = "select * fromlike where mid = ? and aid = ?";
		
		db.getRow(sql, new LikeRowMapper(), mid, aid);
				
		return false;
	}
	

	public ArrayList<Like> getLikeByArticle() {
		String sql = "SELECT * FROM `like`";
		return db.getRows(sql, new LikeRowMapper());
	}
	
	

}
