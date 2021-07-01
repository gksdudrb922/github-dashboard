package repository;

import org.kohsuke.github.GHIssueComment;

import java.io.IOException;
import java.util.*;

public class UserRepository {

    private static Map<String, Set> dashboard = new HashMap<>();

    public static void addUserByComments(Integer issueNumber, List<GHIssueComment> comments) {
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

    public static Map<String, Set> getDashboard() {
        return dashboard;
    }
}
