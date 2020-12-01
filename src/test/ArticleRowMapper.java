package test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article>{

	@Override
	public Article getRow(ResultSet rs) throws SQLException {
		String title = rs.getString("title");
		int id = rs.getInt("id");
		String body = rs.getString("body");
		String nickname = rs.getString("nickname");
		int hit = rs.getInt("hit");

		Article article = new Article();
		article.setTitle(title);
		article.setBody(body);
		article.setNickname(nickname);
		article.setId(id);
		article.setHit(hit);

		return article;
	}

}
