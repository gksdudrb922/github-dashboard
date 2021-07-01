import repository.IssueRepository;
import repository.UserRepository;

import java.util.Map;
import java.util.Set;

public class OutputView {
    public static void printDashboard() {

        int issueSize = IssueRepository.getIssueMap().size();
        Map<String, Set> dashboard = UserRepository.getDashboard();

        //첫 번째 행
        System.out.printf("| 참여자(%d) |",dashboard.size());
        for (int i = 1; i <= issueSize; i++) {
            System.out.printf(" %d주차 |", i);
        }
        System.out.println(" 참석율 |");

        //두 번째 행
        System.out.print("| --- |");
        for (int i = 1; i <= issueSize; i++) {
            System.out.printf(" --- |", i);
        }
        System.out.println(" --- |");

        //대시보드 내용
        dashboard.forEach((username, issueNumbers)-> {
            System.out.printf("| %s |",username);
            for (int i = 1; i <= issueSize; i++) {
                if (issueNumbers.contains(i)) {
                    System.out.print(":white_check_mark:|");
                } else {
                    System.out.printf("|");
                }
            }
            System.out.printf(" %.2f%% |\n", issueNumbers.size() * 100 / (double) issueSize);
        });
    }
}
