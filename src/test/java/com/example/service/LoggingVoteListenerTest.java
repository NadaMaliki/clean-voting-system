package com.example.service;

import com.example.vote.model.Vote;
import com.example.vote.observer.LoggingVoteListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LoggingVoteListenerTest {

    @Test
    void testOnVote() {
        LoggingVoteListener listener = new LoggingVoteListener();
        Vote vote = new Vote("v1", "Alice", 123L);

        assertDoesNotThrow(() -> listener.onVote(vote));
    }
}

