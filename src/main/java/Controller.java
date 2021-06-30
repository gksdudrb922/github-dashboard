import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class Controller {

    private static Map<Integer, GHIssue> issueMap = new HashMap<>();
    private static Map<String, Set<Integer>> dashboard = new HashMap<>();

    public void run() throws IOException {

        GitHub gitHub = new GitHubBuilder().withOAuthToken(Properties.PERSONAL_ACCESS_TOKEN).build();
        List<GHIssue> issues = gitHub.getRepository(Properties.TARGET_REPOSITORY).getIssues(GHIssueState.ALL);

        if (issues.isEmpty()) {
            System.out.println("No Issues");
            return;
        }

        // issues -> HashMap으로 변환
        issues.stream()
                .forEach(issue -> {
                    issueMap.put(issue.getNumber(), issue);
        });

        // userMap 생성
        issueMap.forEach((issueNumber, issue)-> {
            try {
                addUserByComments(issueNumber, issue.getComments());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /**
         * 대시보드 출력
         */
        //첫 번째 행
        System.out.printf("| 참여자(%d) |",dashboard.size());
        for (int i = 1; i <= issues.size(); i++) {
            System.out.printf(" %d주차 |", i);
        }
        System.out.println(" 참석율 |");

        //두 번째 행
        System.out.print("| --- |");
        for (int i = 1; i <= issues.size(); i++) {
            System.out.printf(" --- |", i);
        }
        System.out.println(" --- |");

        //대시보드 내용
        dashboard.forEach((username, issueNumbers)-> {
            System.out.printf("| %s |",username);
            for (int i = 1; i <= issues.size(); i++) {
                if (issueNumbers.contains(i)) {
                    System.out.print(":white_check_mark:|");
                } else {
                    System.out.printf("|");
                }
            }
            System.out.printf(" %.2f%% |\n", issueNumbers.size() * 100 / (double) issues.size());
        });
    }

    private void addUserByComments(Integer issueNumber, List<GHIssueComment> comments) {

        comments.stream()
                .forEach(comment -> {
                    try {
                        String username = comment.getUser().getLogin();
                        if (!dashboard.containsKey(username)) {
                            dashboard.put(username, new HashSet<Integer>());
                        }
                        dashboard.get(username).add(issueNumber);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
