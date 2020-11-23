package teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 드라이버 정보
	String driver = "com.mysql.cj.jdbc.Driver";
	// dbms 주소
	String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";

	// 사용자 계정
	String user = "sbsst";
	// 사용자 비밀번호
	String pass = "sbs123414";

	public ArrayList<Article> getRows(String sql) {
		ArrayList<Article> articles = new ArrayList<>();

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articles;
	}

	public ArrayList<Article> getRows(String sql, String[] params) {
		ArrayList<Article> articles = new ArrayList<>();

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);

			String[] fields = sqlCheck(sql);
			if (fields != null) {
				for (int i = 0; i < fields.length; i++) {
					if (fields.equals("hit") || fields.equals("id")) {
						pstmt.setInt(i + 1, Integer.parseInt(params[i]));
					} else {
						pstmt.setString(i + 1, params[i]);
					}
				}
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
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

				articles.add(article);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return articles;
	}

	public void setData(String sql) {
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String[] sqlCheck(String sql) {

		String[] stringBits = null;

		if (sql.startsWith("select") || sql.startsWith("delete")) {
			int index = sql.indexOf(" where ");
			if (index != -1) {
				sql = sql.substring(index + 7);

				stringBits = sql.split("and");

				for (int i = 0; i < stringBits.length; i++) {
					stringBits[i] = stringBits[i].replace(" ", "");
				}
				for (int i = 0; i < stringBits.length; i++) {
					String[] tmp = stringBits[i].split("=");
					stringBits[i] = tmp[0].replace(" ", "");
				}
			}
		}

		return stringBits;
	}

	public ArrayList<Article> getArticles() {

		ArrayList<Article> articles = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			String sql = "SELECT * FROM article";
			pstmt = conn.prepareStatement(sql); // PreparedStatment 통해서 sql 세팅

			rs = pstmt.executeQuery(); // 조회 결과가 있는 경우
			// pstmt.executeUpdate(); // 조회 결과가 없는 경우

			while (rs.next()) {
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

				articles.add(article);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
			close();
		}

		return articles;
	}

	public void addArticle(String title, String body) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			String sql2 = " INSERT INTO article SET title = ?, `body` = ?, nickname = '홍길동', hit = 10";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);

			pstmt2.setString(1, title);
			pstmt2.setString(2, body);
			pstmt2.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
			close();
		}
	}

	public Article getArticleById(int id) {

		Article article = new Article();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			String sql2 = "select * from article where id = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, id);

			rs = pstmt2.executeQuery();

			if (rs.next()) {
				String title = rs.getString("title");
				article.setTitle(title);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
			close();
		}

		return article;
	}

	public void updateArticle(int id, String title, String body) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			String sql = "UPDATE article SET title = ?, `body` = ? WHERE id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, id);

			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteArticle(int id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, pass);

			String sql = "delete from article WHERE id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
			close();
		}
	}

	public Connection getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}