package com.example.darshi.homescreen;

public class IssueMaster {
    String issue_description,voting_deadline,deadline_time;
    String yes_votes,no_votes;
    IssueMaster(){}

    public String getIssue_description() {
        return issue_description;
    }

    public void setIssue_description(String issue_description) {
        this.issue_description = issue_description;
    }

    public String getVoting_deadline() {
        return voting_deadline;
    }

    public void setVoting_deadline(String voting_deadline) {
        this.voting_deadline = voting_deadline;
    }

    public String getYes_votes() {
        return yes_votes;
    }

    public void setYes_votes(String yes_votes) {
        this.yes_votes = yes_votes;
    }

    public String getNo_votes() {
        return no_votes;
    }

    public void setNo_votes(String no_votes) {
        this.no_votes = no_votes;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }
}
