package board;

import java.util.ArrayList;
import java.util.Scanner;

import board.article.Article;
import board.article.ArticleDao;
import board.article.Reply;
import board.member.Member;
import board.member.MemberDao;

public class App {
	
	ArticleDao articleDao = new ArticleDao();
	MemberDao memberDao = new MemberDao();
	Member loginedMember = null;

	Scanner sc = new Scanner(System.in);
	
	public void start() {
		while (true) {
			inputCommmand();
			
			String cmd = sc.nextLine();
			
			if (cmd.equals("list")) {
				articleList();
			} else if (cmd.equals("update")) {
				articleUpdate();
			} else if (cmd.equals("delete")) {
				articleDelete();
			} else if (cmd.equals("add")) {
				articleAdd();
			} else if (cmd.equals("read")) {
				articleRead();
			} else if (cmd.equals("signup")) {
				memberSignup();
			} else if (cmd.equals("login")) {
				memberLogin();
			}
		}
	}
	
	private void memberLogin() {
		System.out.print("아이디를 입력해주세요 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호를 입력해주세요 : ");
		String pw = sc.nextLine();

		if (memberDao.GetMemberByIdAndPw(id, pw) != null) {
			ArrayList<Member> members = memberDao.getReplyByLogin(id);

			for (int i = 0; i < members.size(); i++) {
				Member member = members.get(i);
				loginedMember = member;
			}

			String nickname = memberDao.GetMemberByIdAndPw(id, pw).getNickname();
			System.out.println(nickname + "님 환영합니다.");
		} else {
			System.out.println("비밀번호를 틀렸거나 잘못된 회원정보입니다.");
		}
	}

	private void memberSignup() {
		System.out.print("아이디를 입력해주세요 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호를 입력해주세요 : ");
		String pw = sc.nextLine();
		System.out.print("닉네임을 입력해주세요 : ");
		String nickname = sc.nextLine();
		if (memberDao.CheckMemberById(id) != null) {
			System.out.println("중복된 아이디 입니다.");
		} else if (memberDao.CheckMemberByNickName(nickname) != null) {
			System.out.println("중복된 닉네임 입니다.");
		} else {
			memberDao.insertMember(id, pw, nickname);
			System.out.println("회원가입 되었습니다.");
		}
	}

	private void articleRead() {
		if (loginedMember == null) {
			System.out.println("로그인이 필요한 기능입니다.");
		} else {
			System.out.print("상세보기할 게시물 번호 : ");
			int aid = Integer.parseInt(sc.nextLine());

			Article article = articleDao.getArticleById(aid);

			if (article == null) {
				System.out.println("없는 게시물입니다.");
			} else {
				articleDao.hitArticle(aid);
				ArrayList<Reply> replies = articleDao.getReplyByArticleId(article.getId());
				PrintArticle(article, replies);

				while (true) {
					System.out.print("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 좋아요, 3. 수정, 4. 삭제, 5. 목록으로) : ");
					int dcmd = Integer.parseInt(sc.nextLine());
					if (dcmd == 1) {
						System.out.print("내용을 입력해주세요 :");
						String body = sc.nextLine();
						articleDao.insertReply(article.getId(), body, loginedMember.getNickname());
						ArrayList<Reply> replies2 = articleDao.getReplyByArticleId(article.getId());
						PrintArticle(article, replies2);
					} else if (dcmd == 2) {
						
					} else {
						break;
					}
				}
			}

		}

	}

	private void articleAdd() {
		if (loginedMember == null) {
			System.out.println("로그인이 필요한 기능입니다.");
		} else {
			System.out.print("제목 : ");
			String title = sc.nextLine();
			System.out.print("내용 : ");
			String body = sc.nextLine();

			articleDao.insertArticle(title, body, loginedMember.getNickname());
		}
	}

	private void articleDelete() {
		System.out.print("삭제할 게시물 번호 : ");
		int aid = Integer.parseInt(sc.nextLine());
		articleDao.deleteArticle(aid);
	}

	private void articleUpdate() {
		System.out.print("수정할 게시물 번호 : ");
		int aid = Integer.parseInt(sc.nextLine());

		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		articleDao.updateArticle(title, body, aid);
	}

	private void articleList() {
		ArrayList<Article> articles = articleDao.getArticles();
		
		for (int i = 0; i < articles.size(); i++) {
			System.out.println("번호 : " + articles.get(i).getId());
			System.out.println("제목 : " + articles.get(i).getTitle());
			System.out.println("작성자 : " + articles.get(i).getNickname());
			System.out.println("등록날짜 : " + articles.get(i).getRegDate());
			System.out.println("----------------------------------");
		}
		
	}

	private void inputCommmand() {
		if (loginedMember == null) {
			System.out.print("명령어를 입력해주세요 : ");
		} else {
			System.out.print("명령어를 입력해주세요[" + loginedMember.getId() + "(" + loginedMember.getNickname() + ")] : ");
		}
	}

	public void PrintArticle(Article article, ArrayList<Reply> replies) {
		System.out.println("번호 : " + article.getId());
		System.out.println("제목 : " + article.getTitle());
		System.out.println("내용 : " + article.getBody());
		System.out.println("작성자 : " + article.getNickname());
		System.out.println("등록날짜 : " + article.getRegDate());
		System.out.println("조회수 : " + article.getHit());
		System.out.println("======== 댓글 =======");
		for (int i = 0; i < replies.size(); i++) {
			System.out.println("내용 : " + replies.get(i).getBody());
			System.out.println("작성자 : " + replies.get(i).getWriter());
			System.out.println("------------------");
		}
	}
}
