import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.exceptions.SQLError;

public class test {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		// =======================================DB
		// 세팅=========================================

		// 드라이버 정보
		String driver = "com.mysql.cj.jdbc.Driver";
		// dbms 주소
		String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";

//		// 집 버전
//		// 사용자 계정
//		String user = "root";
//		// 사용자 비밀번호
//		String pass = "";

		// 학원버전
		// 사용자 계정
		String user = "sbsst";
		// 사용자 비밀번호
		String pass = "sbs123414";

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

			}
			
			while (true) {
			
				System.out.print("명령어를 입력해주세요 : ");
				String cmd = sc.nextLine();
	
				if (cmd.equals("list")) {
					String sql2 = "SELECT * FROM article";
	
					PreparedStatement pstmt2 = conn.prepareStatement(sql2); // PreparedStatment 통해서 sql 세팅

					ResultSet rs2 = pstmt.executeQuery(); // 조회 결과가 있는 경우
					// pstmt.executeUpdate(); // 조회 결과가 없는 경우
					
					while (rs2.next()) {
						String title = rs2.getString("title");
						int id = rs2.getInt("id");
						String body = rs2.getString("body");
						String nickname = rs2.getString("nickname");
						int hit = rs2.getInt("hit");
						System.out.println(id + " " + title + " " + body + " " + nickname + " " + hit);
					}
				}
	
				if (cmd.equals("add")) {
					System.out.print("제목 : ");
					String title = sc.nextLine();
	
					System.out.print("내용 : ");
					String body = sc.nextLine();
	
					String sql3 = "INSERT INTO article SET title = ?, `body` = ?, nickname = '홍길동', hit = 10";
					PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	
					pstmt3.setString(1, title);
					pstmt3.setString(2, body);
					pstmt3.executeUpdate();
				}
				
				if (cmd.equals("update")) {
					System.out.print("수정할 게시물 선택 : ");
					int id = Integer.parseInt(sc.nextLine());

					String sql4 = "SELECT * FROM article WHERE id = ?";
					PreparedStatement pstmt4 = conn.prepareStatement(sql4);
					pstmt4.setInt(1, id);

					ResultSet rs4 = pstmt4.executeQuery();

					if (rs4.next()) {
						System.out.print("제목 : ");
						String title = sc.nextLine();

						System.out.print("내용 : ");
						String body = sc.nextLine();

						String sql5 = "UPDATE article SET title = ?, `body` = ? WHERE id = ?";
						PreparedStatement pstmt5 = conn.prepareStatement(sql5);

						pstmt5.setString(1, title);
						pstmt5.setString(2, body);
						pstmt5.setInt(3, id);
						pstmt5.executeUpdate();
						System.out.println(id + "번 게시물이 수정되었습니다.");
					} else {
						System.out.println("없는 게시물 입니다.");
					}

				}

				if (cmd.equals("read")) {
					System.out.print("조회할 게시물 선택 : ");
					int id3 = Integer.parseInt(sc.nextLine());

					String sql6 = "SELECT * FROM article WHERE id = ?";
					PreparedStatement pstmt6 = conn.prepareStatement(sql6);
					pstmt6.setInt(1, id3);

					ResultSet rs6 = pstmt6.executeQuery();
					
					if (rs.next()) {
						int id2 = rs6.getInt("id");
						String title = rs6.getString("title");
						String body = rs6.getString("body");

						System.out.println("번호 : " + id2);
						System.out.println("제목 : " + title);
						System.out.println("내용 : " + body);
					} else {
						System.out.println("없는 게시물 입니다.");
					}

				}

				if (cmd.equals("delete")) {
					System.out.print("삭제할 게시물 선택 : ");
					int id = Integer.parseInt(sc.nextLine());

					String sql7 = "SELECT * FROM article WHERE id = ?";
					PreparedStatement pstmt7 = conn.prepareStatement(sql7);
					pstmt7.setInt(1, id);

					ResultSet rs7 = pstmt7.executeQuery();

					if (rs.next()) {
						String sql8 = "DELETE FROM article WHERE id = ?";
						PreparedStatement pstmt8 = conn.prepareStatement(sql8);
						pstmt.setInt(1, id);
						pstmt.executeUpdate();

						System.out.println(id + "번 게시물이 삭제되었습니다.");
					} else {
						System.out.println("없는 게시물 입니다.");
					}

				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// DBMS 선택 -> 담당자(Connection) 부여받음
			e2.printStackTrace();
		} finally {
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

//		while (true) {
//
//			System.out.print("명령어를 입력해주세요 : ");
//			String cmd = sc.nextLine();
//
//			if (cmd.equals("list")) {
//				String sql = "SELECT * FROM article";
//
//				PreparedStatement pstmt = conn.prepareStatement(sql); // PreparedStatment 통해서 sql 세팅
//
//				ResultSet rs = pstmt.executeQuery(); // 조회 결과가 있는 경우
//				// pstmt.executeUpdate(); // 조회 결과가 없는 경우
//
//				while (rs.next()) {
//					String title = rs.getString("title");
//					int id = rs.getInt("id");
//					String body = rs.getString("body");
//					String nickname = rs.getString("nickname");
//					int hit = rs.getInt("hit");
//					System.out.println(id + " " + title + " " + body + " " + nickname + " " + hit);
//				}
//			}
//
//			if (cmd.equals("add")) {
//				System.out.print("제목 : ");
//				String title = sc.nextLine();
//
//				System.out.print("내용 : ");
//				String body = sc.nextLine();
//
//				String sql = "INSERT INTO article SET title = ?, `body` = ?, nickname = '홍길동', hit = 10";
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//
//				pstmt.setString(1, title);
//				pstmt.setString(2, body);
//				pstmt.executeUpdate();
//			}
//
//			if (cmd.equals("update")) {
//				System.out.print("수정할 게시물 선택 : ");
//				int id = Integer.parseInt(sc.nextLine());
//
//				String sql2 = "SELECT * FROM article WHERE id = ?";
//				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//				pstmt2.setInt(1, id);
//
//				ResultSet rs = pstmt2.executeQuery();
//
//				if (rs.next()) {
//					System.out.print("제목 : ");
//					String title = sc.nextLine();
//
//					System.out.print("내용 : ");
//					String body = sc.nextLine();
//
//					String sql = "UPDATE article SET title = ?, `body` = ? WHERE id = ?";
//					PreparedStatement pstmt = conn.prepareStatement(sql);
//
//					pstmt.setString(1, title);
//					pstmt.setString(2, body);
//					pstmt.setInt(3, id);
//					pstmt.executeUpdate();
//					System.out.println(id + "번 게시물이 수정되었습니다.");
//				} else {
//					System.out.println("없는 게시물 입니다.");
//				}
//
//			}
//
//			if (cmd.equals("read")) {
//				System.out.print("조회할 게시물 선택 : ");
//				int id = Integer.parseInt(sc.nextLine());
//
//				String sql2 = "SELECT * FROM article WHERE id = ?";
//				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//				pstmt2.setInt(1, id);
//
//				ResultSet rs = pstmt2.executeQuery();
//
//				if (rs.next()) {
//					int id2 = rs.getInt("id");
//					String title = rs.getString("title");
//					String body = rs.getString("body");
//
//					System.out.println("번호 : " + id2);
//					System.out.println("제목 : " + title);
//					System.out.println("내용 : " + body);
//				} else {
//					System.out.println("없는 게시물 입니다.");
//				}
//
//			}
//
//			if (cmd.equals("delete")) {
//				System.out.print("삭제할 게시물 선택 : ");
//				int id = Integer.parseInt(sc.nextLine());
//
//				String sql2 = "SELECT * FROM article WHERE id = ?";
//				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//				pstmt2.setInt(1, id);
//
//				ResultSet rs = pstmt2.executeQuery();
//
//				if (rs.next()) {
//					String sql = "DELETE FROM article WHERE id = ?";
//					PreparedStatement pstmt = conn.prepareStatement(sql);
//					pstmt.setInt(1, id);
//					pstmt.executeUpdate();
//
//					System.out.println(id + "번 게시물이 삭제되었습니다.");
//				} else {
//					System.out.println("없는 게시물 입니다.");
//				}
//
//			}
//
//		}

	}
}