
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class java1118 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// // =======================================DB
		// ����=========================================

		// ����̹� ����
		String driver = "com.mysql.cj.jdbc.Driver";
		// dbms �ּ�
		String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";
		
		// �� ����
		// ����� ����
		String user = "root";
		// ����� ��й�ȣ
		String pass = "";

//		// �п�����
//		// ����� ����
//		String user = "sbsst";
//		// ����� ��й�ȣ
//		String pass = "123414";
		
		Class.forName(driver); // Driver ����
		Connection conn = DriverManager.getConnection(url, user, pass); // DBMS ���� -> �����(Connection) �ο�����

		Scanner sc = new Scanner(System.in);
		while (true) {

			System.out.print("��ɾ �Է����ּ��� : ");
			String cmd = sc.nextLine();

			if (cmd.equals("list")) {
				String sql = "SELECT * FROM article";

				PreparedStatement pstmt = conn.prepareStatement(sql); // PreparedStatment ���ؼ� sql ����

				ResultSet rs = pstmt.executeQuery(); // ��ȸ ����� �ִ� ���
				// pstmt.executeUpdate(); // ��ȸ ����� ���� ���

				while (rs.next()) {
					String title = rs.getString("title");
					int id = rs.getInt("id");
					String body = rs.getString("body");
					String nickname = rs.getString("nickname");
					int hit = rs.getInt("hit");
					System.out.println(id + " " + title + " " + body + " " + nickname + " " + hit);
				}
			}

			if (cmd.equals("add")) {
				System.out.print("���� : ");
				String title = sc.nextLine();

				System.out.print("���� : ");
				String body = sc.nextLine();

				String sql = "INSERT INTO article SET title = ?, `body` = ?, nickname = 'ȫ�浿', hit = 10";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.executeUpdate();
			}

			if (cmd.equals("update")) {
				System.out.print("������ �Խù� ���� : ");
				int id = Integer.parseInt(sc.nextLine());

				String sql2 = "SELECT * FROM article WHERE id = ?";
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, id);

				ResultSet rs = pstmt2.executeQuery();

				if (rs.next()) {
					System.out.print("���� : ");
					String title = sc.nextLine();

					System.out.print("���� : ");
					String body = sc.nextLine();

					String sql = "UPDATE article SET title = ?, `body` = ? WHERE id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, title);
					pstmt.setString(2, body);
					pstmt.setInt(3, id);
					pstmt.executeUpdate();
					System.out.println(id + "�� �Խù��� �����Ǿ����ϴ�.");
				} else {
					System.out.println("���� �Խù� �Դϴ�.");
				}

			}

			if (cmd.equals("read")) {
				System.out.print("��ȸ�� �Խù� ���� : ");
				int id = Integer.parseInt(sc.nextLine());

				String sql2 = "SELECT * FROM article WHERE id = ?";
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, id);

				ResultSet rs = pstmt2.executeQuery();

				if (rs.next()) {
					int id2 = rs.getInt("id");
					String title = rs.getString("title");
					String body = rs.getString("body");

					System.out.println("��ȣ : " + id2);
					System.out.println("���� : " + title);
					System.out.println("���� : " + body);
				} else {
					System.out.println("���� �Խù� �Դϴ�.");
				}

			}

			if (cmd.equals("delete")) {
				System.out.print("������ �Խù� ���� : ");
				int id = Integer.parseInt(sc.nextLine());

				String sql2 = "SELECT * FROM article WHERE id = ?";
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, id);

				ResultSet rs = pstmt2.executeQuery();

				if (rs.next()) {
					String sql = "DELETE FROM article WHERE id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();

					System.out.println(id + "�� �Խù��� �����Ǿ����ϴ�.");
				} else {
					System.out.println("���� �Խù� �Դϴ�.");
				}

			}

		}

	}
}