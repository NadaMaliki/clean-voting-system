package com.example.vote.strategy;

import com.example.vote.model.Vote;

import java.util.*;

public class PluralityCountingStrategy implements CountingStrategy {

    @Override
    public Map<String, Integer> count(List<Vote> votes) {
        Map<String, Integer> result = new HashMap<>();

        for (var v : votes) {
            String cid = v.getCandidateId();
            result.merge(cid, 1, Integer::sum);
        }
        return result;
    }
}
