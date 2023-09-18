import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardApp {

    ArrayList<Article> articles = new ArrayList<>();

    public void start() {

        Article a1 = new Article(1,"안녕하세요 반갑습니다. 자바공부 중입니다.","열심히하겠습니다.",getCurrentDate());
        Article a2 = new Article(2,"자바 질문좀 할게요","열심히 하겠습니다",getCurrentDate());
        Article a3 = new Article(3,"정처기 따야되나요?","열심히 하겠습니다",getCurrentDate());

        articles.add(a1);
        articles.add(a2);
        articles.add(a3);

        Scanner sc = new Scanner(System.in);
        int lastArticleId = 4;

        while (true) {
            System.out.print("메뉴 입력 : ");
            String command = sc.nextLine();
            if (command.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (command.equals("add")) {
                System.out.print("게시물 제목을 입력해주세요 : ");
                String title = sc.nextLine();
                System.out.print("게시물 내용을 입력해주세요 : ");
                String content = sc.nextLine();

                Article article = new Article(lastArticleId, title, content, getCurrentDate());

                articles.add(article);
                lastArticleId++;

                System.out.println("게시물이 등록되었습니다.");

            } else if (command.equals("list")) {
                printArticles(articles);

            } else if (command.equals("update")) {
                System.out.printf("수정할 게시판 번호 : ");
                int targetId = getParamInt(sc.nextLine(), -1);
                if (targetId == -1) {
                    continue;
                }


                Article article = findById(targetId);

                if (article == null) {
                    System.out.println("없는 게시물입니다.");

                } else {
                    System.out.print("제목 : ");
                    String newTitle = sc.nextLine();
                    System.out.print("내용 : ");
                    String newContent = sc.nextLine();

                    article.setTitle(newTitle);
                    article.setContent(newContent);

                    System.out.println("수정이 완료되었습니다.");
                }


            } else if (command.equals("delete")) {
                System.out.print("삭제할 게시판 번호 : ");
                int targetId = getParamInt(sc.nextLine(), -1);
                if (targetId == -1) {
                    continue;
                }

                Article article = findById(targetId);

                if (article == null) {
                    System.out.println("없는 게시물입니다.");

                } else {
                    articles.remove(article);
                    System.out.printf("%d번 게시물이 삭제되었습니다\n", targetId);
                }
            } else if (command.equals("detail")) {
                System.out.print("상세보기할 번호 : ");
                int targetId = getParamInt(sc.nextLine(), -1);
                if (targetId == -1) {
                    continue;
                }

                Article article = findById(targetId);

                if (article == null) {
                    System.out.println("없는 게시물입니다.");

                } else {

                    article.setHit(article.getHit() + 1);

                    System.out.println("===================");
                    System.out.printf("번호 : %d\n", article.getId());
                    System.out.printf("제목 : %s\n", article.getTitle());
                    System.out.printf("내용 : %s\n", article.getContent());
                    System.out.printf("조회수 : %d\n", article.getHit());
                    System.out.printf("등록날짜 : %s\n", article.getRegDate());
                }
            } else if (command.equals("search")) {
                System.out.print("검색 키워드를 입력해주세요 :");
                String keyword = sc.nextLine();

                System.out.println("===================");
                for (int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);
                    String title = article.getTitle();

                    ArrayList<Article> searchedArticles = new ArrayList<>();

                    if (title.contains(keyword)) {
                        printArticles(searchedArticles);
                    }
                }
            }
        }
    }

    public Article findById(int id) {

        Article target = null;

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);

            if (id == article.getId()) {
                target = article;
            }
        }
        return target;
    }

    public int getParamInt(String input, int defaultValue) {
        try {
            int num = Integer.parseInt(input);
            return num;

        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력하세요");
        }
        return defaultValue;
    }

    public String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
        String formatedNow = now.format(formatter);

        return formatedNow;
    }

    public void printArticles(ArrayList<Article> list) {
        System.out.println("===================");

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);

            System.out.printf("번호 : %d\n", article.getId());
            System.out.printf("제목 : %s\n", article.getTitle());
            System.out.println("===================");
        }

    }


}
