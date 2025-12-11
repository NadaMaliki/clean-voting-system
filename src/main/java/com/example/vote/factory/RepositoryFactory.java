package com.example.vote.factory;

import com.example.vote.repo.*;

public class RepositoryFactory {

    private RepositoryFactory() {
    }

    public static VoteRepository createRepository(String type) {
        if ("memory".equalsIgnoreCase(type)) {
            return new InMemoryVoteRepository();
        } else if ("jdbc".equalsIgnoreCase(type)) {
            return new JdbcVoteRepository();
        }
        throw new IllegalArgumentException("Unknown repository type: " + type);
    }
}
