package com.example.service;

import com.example.vote.repo.InMemoryVoteRepository;
import com.example.vote.model.Vote;
import com.example.vote.service.VoteService;
import com.example.vote.strategy.PluralityCountingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VoteServiceTest {

    @Test
    void testCastAndCount() {

        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        service.cast(new Vote("v1", "Alice", 1));
        service.cast(new Vote("v2", "Alice", 2));
        service.cast(new Vote("v3", "Bob", 3));

        Map<String, Integer> result = service.count(new PluralityCountingStrategy());

        assertEquals(2, result.get("Alice"));
        assertEquals(1, result.get("Bob"));
    }

    @Test
    void testDuplicateVote() {
        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.cast(new Vote("v1", "Alice", 1));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            svc.cast(new Vote("v1", "Bob", 2));
        });
        assertTrue(ex.getMessage().contains("already voted"));
    }

    @Test
    void testAddRemoveCandidate() {
        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.addCandidate("Charlie");
        svc.cast(new Vote("v1", "Charlie", 1L));
        Map<String, Integer> res = svc.count(new PluralityCountingStrategy());
        assertEquals(1, res.get("Charlie"));

        svc.removeCandidate("Charlie");
        res = svc.count(new PluralityCountingStrategy());
        assertFalse(res.containsKey("Charlie"));
    }

    @Test
    void testVoteHistory() {
        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.cast(new Vote("v1", "Alice", 1L));
        svc.cast(new Vote("v2", "Bob", 2L));

        var history = svc.getHistory();
        assertEquals(2, history.size());
        assertEquals("Alice", history.get(0).getCandidateId());
    }


}
