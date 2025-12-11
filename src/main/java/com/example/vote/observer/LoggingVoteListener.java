package com.example.vote.observer;

import com.example.vote.model.Vote;

public class LoggingVoteListener implements VoteListener {
    @Override
    public void onVote(Vote vote) {
        System.out.println("[LOG] Vote received: " + vote);
    }
}
