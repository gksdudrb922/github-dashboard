package repository;

import org.kohsuke.github.GHIssue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueRepository {

    private static Map<Integer, GHIssue> issueMap = new HashMap<>();

    public static void addIssueListToMap(List<GHIssue> issues) {
        issues.stream()
                .forEach(issue -> {
                    issueMap.put(issue.getNumber(), issue);
                });
    }

    public static Map<Integer, GHIssue> getIssueMap() {
        return issueMap;
    }
}
