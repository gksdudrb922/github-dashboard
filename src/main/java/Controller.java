import org.kohsuke.github.*;
import repository.IssueRepository;
import repository.UserRepository;

import java.io.IOException;
import java.util.*;

public class Controller {

    private static GitHub gitHub;

    public void run() throws IOException {

        connectToGitHub();
        loadIssues();
        updateDashboard();
        OutputView.printDashboard();
    }

    private void connectToGitHub() throws IOException {
        gitHub = new GitHubBuilder().withOAuthToken(Properties.PERSONAL_ACCESS_TOKEN).build();
    }

    private void loadIssues() throws IOException {
        List<GHIssue> issues = gitHub.getRepository(Properties.TARGET_REPOSITORY).getIssues(GHIssueState.ALL);
        if (issues.isEmpty()) {
            throw new IllegalStateException("No Issues.");
        }
        IssueRepository.addIssueListToMap(issues);
    }

    private void updateDashboard() {
        IssueRepository.getIssueMap().forEach((issueNumber, issue)-> {
            try {
                UserRepository.addUserByComments(issueNumber, issue.getComments());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
