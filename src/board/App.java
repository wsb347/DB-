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

			if (cmd.equals("article list")) {
				articleList();
			} else if (cmd.equals("article update")) {
				articleUpdate();
			} else if (cmd.equals("article delete")) {
				articleDelete();
			} else if (cmd.equals("article add")) {
				articleAdd();
			} else if (cmd.equals("article read")) {
				articleRead();
			} else if (cmd.equals("article sort")) {
				articleSort();
			} else if (cmd.equals("article search")) {
				articleSearch();
			} else if (cmd.equals("member signup")) {
				memberSignup();
			} else if (cmd.equals("member login")) {
				memberLogin();
			} else if (cmd.equals("member logout")) {
				memberLogout();
			} else if (cmd.equals("help")) {
				cmdHelp();
			}
		}
	}

	private void articleSort() {
		String sortFlag = sc.nextLine();
		String sortType = sc.nextLine();
		
		ArrayList<Article> sortedArticles = articleDao.getSortedArticles(sortFlag, sortType);

		printArticles(sortedArticles);
	}

	public void articleSearch() {
//		System.out.println("검색 할 항목을 선택해주세요");
//		System.out.println("(1. 제목, 2. 내용, 3. 제목 + 내용, 4. 작성자)");
//		int targetId = Integer.parseInt(sc.nextLine());
//		if(targetId == 1) {
//			System.out.print("검색 키워드를 입력해주세요 : ");
//			String title = sc.nextLine();
//			ArrayList<Article> article = articleDao.SearchArticleByTitle(title);
//			if(article == null) {
//				System.out.println("해당 게시물이 없습니다.");
//			} else {
//				ArrayList<Article> searchArticle = articleDao.SearchArticleByTitle(title);
//				PrintArticles(searchArticle);
//			}
//		} else if(targetId == 2) {
//			System.out.print("검색 키워드를 입력해주세요 : ");
//			String body = sc.nextLine();
//			ArrayList<Article> article = articleDao.SearchArticleByBody(body);
//			if(article == null) {
//				System.out.println("해당 게시물이 없습니다.");
//			} else {
//				ArrayList<Article> searchArticle = articleDao.SearchArticleByBody(body);
//				PrintArticles(searchArticle);
//			}
//		} else if(targetId == 3) {
//			System.out.print("검색 키워드를 입력해주세요 : ");
//			String body = sc.nextLine();
//			ArrayList<Article> article = articleDao.SearchArticleByBody(body);
//			if(article == null) {
//				System.out.println("해당 게시물이 없습니다.");
//			} else {
//				ArrayList<Article> searchArticle = articleDao.SearchArticleByBody(body);
//				PrintArticles(searchArticle);
//			}
//		}
		
		System.out.println("검색 할 항목을 선택해주세요");
		System.out.println("(1. 제목, 2. 내용, 3. 제목 + 내용, 4. 작성자)");
		int searchFlag = Integer.parseInt(sc.nextLine());
		
		System.out.print("검색 키워드를 입력해주세요 : ");
		String keyword = sc.nextLine();
		
		ArrayList<Article> article = articleDao.SearchArticle(searchFlag, keyword);
		if(article == null) {
			System.out.println("해당 게시물이 없습니다.");
		} else {
			ArrayList<Article> searchArticle = articleDao.SearchArticle(searchFlag, keyword);
			printArticles(searchArticle);
		}
		

		
	}

	public void memberLogout() {
		loginedMember = null;
	}

	public void cmdHelp() {
		System.out.println("article [add: 게시물 추가 / list : 게시물 목록 조회 /\nread : 게시물 조회 / search : 검색]");
		System.out.println(
				"member [signup : 회원가입 / signin : 로그인 /\nfindpass : 비밀번호 찾기 / findid : 아이디 찾기 /\nlogout : 로그아웃 / myinfo : 나의 정보 확인및 수정]");
	}

	public void memberLogin() {
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

	public void memberSignup() {
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

	public void articleRead() {
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
//						if(loginedMember.getCheckNo() == 1) {
//							loginedMember.setCheckNo(0);
//							articleDao.MinusLikebyArticle(article.getId());
//							System.out.println("좋아요" + article2.getLike());
//						} else {
//							loginedMember.setCheckNo(1);
//							articleDao.PlusSetLikebyArticle(article.getId());
//							System.out.println("좋아요" + article2.getLike());
//						}
						break;
					} else {
						break;
					}
				}
			}

		}

	}

	public void articleAdd() {
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

	public void articleDelete() {
		System.out.print("삭제할 게시물 번호 : ");
		int aid = Integer.parseInt(sc.nextLine());
		articleDao.deleteArticle(aid);
	}

	public void articleUpdate() {
		System.out.print("수정할 게시물 번호 : ");
		int aid = Integer.parseInt(sc.nextLine());

		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		articleDao.updateArticle(title, body, aid);
	}

	public void articleList() {
		ArrayList<Article> articles = articleDao.getArticles();

		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);

			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			System.out.println("작성자 : " + article.getNickname());
			System.out.println("등록날짜 : " + article.getRegDate());
			System.out.println("----------------------------------");
		}

	}

	public void inputCommmand() {
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
		Article article2 = articleDao.getArticleById(article.getId());
		System.out.println("조회수 : " + article2.getHit());
		System.out.println("좋아요 : " + article2.getLike());
		System.out.println("======== 댓글 =======");
		for (int i = 0; i < replies.size(); i++) {
			System.out.println("내용 : " + replies.get(i).getBody());
			System.out.println("작성자 : " + replies.get(i).getWriter());
			System.out.println("------------------");
		}
	}
	
	public void printArticles(ArrayList<Article> articles) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			
			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			System.out.println("작성자 : " + article.getNickname());
			System.out.println("등록날짜 : " + article.getRegDate());
			System.out.println("조회수 : " + article.getHit());
			System.out.println("=============================");
		}
	}
}
