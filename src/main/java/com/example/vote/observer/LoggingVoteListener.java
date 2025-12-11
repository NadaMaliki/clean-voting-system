package com.example.vote.observer;

import com.example.vote.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingVoteListener implements VoteListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggingVoteListener.class);

    @Override
    public void onVote(Vote vote) {
        logger.info("Vote received: {}", vote);
    }
}
