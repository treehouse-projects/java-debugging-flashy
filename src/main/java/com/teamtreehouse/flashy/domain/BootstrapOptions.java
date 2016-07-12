package com.teamtreehouse.flashy.domain;

public class BootstrapOptions {
  private boolean shouldFork;
  private String githubOauth;

  public boolean isShouldFork() {
    return shouldFork;
  }

  public void setShouldFork(boolean shouldFork) {
    this.shouldFork = shouldFork;
  }

  public String getGithubOauth() {
    return githubOauth;
  }

  public void setGithubOauth(String githubOauth) {
    this.githubOauth = githubOauth;
  }
}
