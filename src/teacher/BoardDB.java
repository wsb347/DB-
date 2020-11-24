package teacher;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardDB {

	public static void main(String[] args) {
		ArticleDao articleDao = new ArticleDao();

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어를 입력해주세요 : ");
			String cmd = sc.nextLine();
			if(cmd.equals("list")) {
				ArrayList<Article> articles = articleDao.getArticles();
				for(int i = 0; i < articles.size(); i++) {
					System.out.println("번호 : " + articles.get(i).getId());
					System.out.println("제목 : " + articles.get(i).getTitle());
					System.out.println("내용 : " + articles.get(i).getBody());
					System.out.println("작성자 : " + articles.get(i).getNickname());
					System.out.println("조회수 : " + articles.get(i).getHit());
				}
			} else if(cmd.equals("update")) {
				System.out.print("수정할 게시물 번호 : ");
				int aid = Integer.parseInt(sc.nextLine());
				
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				articleDao.updateArticle(title, body, aid);
			} else if(cmd.equals("delete")) {
				System.out.print("삭제할 게시물 번호 : ");
				int aid = Integer.parseInt(sc.nextLine());
				articleDao.deleteArticle(aid);
			} else if(cmd.equals("add")) {
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				articleDao.insertArticle(title, body);
			} else if(cmd.equals("read")) {
				System.out.print("상세보기할 게시물 번호 : ");
				int aid = Integer.parseInt(sc.nextLine());
				
				Article article = articleDao.getArticleById(aid);
				System.out.println("제목 : " + article.getTitle());
				System.out.println("내용 : " + article.getBody());
				System.out.println("작성자 : " + article.getNickname());
				System.out.println("조회수 : " + article.getHit());
			}
		}
	}
}
