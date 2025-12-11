package com.example.service;

import com.example.vote.model.Vote;
import com.example.vote.model.WeightedVote;
import com.example.vote.repo.InMemoryVoteRepository;
import com.example.vote.service.VoteService;
import com.example.vote.strategy.CountingStrategy;
import com.example.vote.strategy.RankedChoiceCountingStrategy;
import com.example.vote.strategy.WeightedPluralityCountingStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankedChoiceCountingStrategyTest {

    @Test
    void testRankedChoice() {
        List<Vote> votes = List.of(
                new Vote("v1", "Alice", 1L),
                new Vote("v2", "Bob", 2L),
                new Vote("v3", "Alice", 3L)
        );

        CountingStrategy strategy = new RankedChoiceCountingStrategy();
        Map<String, Integer> result = strategy.count(votes);

        assertEquals(2, result.get("Alice"));
        assertEquals(1, result.get("Bob"));
    }

    @Test
    void testWeightedVote() {
        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.cast(new WeightedVote("v1", "Alice", 1L, 3));
        svc.cast(new WeightedVote("v2", "Bob", 2L, 2));

        var res = svc.count(new WeightedPluralityCountingStrategy());
        assertEquals(3, res.get("Alice"));
        assertEquals(2, res.get("Bob"));
    }


}
