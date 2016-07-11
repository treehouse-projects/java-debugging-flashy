package com.teamtreehouse.flashy.controllers;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class BootstrapController {
  private final String DEFAULT_TOKEN = "YOUR-TOKEN-HERE";
  private final String GITHUB_MASTER_REPO_OWNER = "treehouse-projects";
  private final String GITHUB_MASTER_REPO_NAME = "java-debugging-flashy";

  public static class WorkLog {
    private List<String> actions;

    public WorkLog() {
      actions = new ArrayList<>();
    }

    public void track(String action, Object... details) {
      actions.add(String.format(action, details));
    }

    public List<String> getActions() {
      return actions;
    }
  }

  @Value("${github.oauth.token}")
  private String oauthToken;

  @RequestMapping("/bootstrap/github")
  public String setupGitHub(@RequestParam(value = "forkIt", defaultValue = "false") boolean forkIt, Model model) {
    WorkLog workLog = new WorkLog();
    try {
      boolean configNeedsUpdate = oauthToken.equals(DEFAULT_TOKEN);
      model.addAttribute("configNeedsUpdate", configNeedsUpdate);
      model.addAttribute("shouldFork", forkIt);
      model.addAttribute("repoName", GITHUB_MASTER_REPO_NAME);
      if (!configNeedsUpdate) {
        String userName = getGitHubUserName();
        model.addAttribute("gitHubUserName", userName);
        if (forkIt) {
          bootstrapRepo(workLog, userName);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    model.addAttribute("actions", workLog.getActions());
    return "bootstrap_github";
  }

  public String getGitHubUserName() throws IOException{
    GitHubClient client = new GitHubClient();
    client.setOAuth2Token(oauthToken);
    UserService userService = new UserService(client);
    User user = userService.getUser();
    return user.getLogin();
  }

  public void bootstrapRepo(WorkLog workLog, String gitHubUserName) throws IOException {
    GitHubClient client = new GitHubClient();
    client.setOAuth2Token(oauthToken);
    RepositoryService repositoryService = new RepositoryService(client);
    IssueService issueService = new IssueService(client);
    Repository repo;
    try {
      repo = repositoryService.getRepository(gitHubUserName, GITHUB_MASTER_REPO_NAME);
    } catch (RequestException re) {
      Repository masterRepo = repositoryService.getRepository(GITHUB_MASTER_REPO_OWNER,
                GITHUB_MASTER_REPO_NAME);
      repo = repositoryService.forkRepository(masterRepo);
      workLog.track("Forked repository %s/%s", GITHUB_MASTER_REPO_OWNER, GITHUB_MASTER_REPO_NAME);
    }
    List<String> existingIssueTitles = new ArrayList<>();
    for (Collection<Issue> existingIssues : issueService.pageIssues(gitHubUserName, repo.getName())) {
      existingIssueTitles.addAll(existingIssues.stream()
              .map(Issue::getTitle)
              .collect(toList()));
    }
    repo.setHasIssues(true);
    repositoryService.editRepository(repo);
    workLog.track("Enabled issues for your repository %s", GITHUB_MASTER_REPO_NAME);
    int issueCount = 0;
    for (Collection<Issue> issues : issueService.pageIssues(GITHUB_MASTER_REPO_OWNER, GITHUB_MASTER_REPO_NAME)) {
      for (Issue issue : issues) {
        if (!existingIssueTitles.contains(issue.getTitle())) {
          issueService.createIssue(gitHubUserName, GITHUB_MASTER_REPO_NAME, issue);
          issueCount++;
        }
      }
    }
    workLog.track("Created %d issues in your repoository", issueCount);
  }
}
