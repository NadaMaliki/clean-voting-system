package com.example.vote.strategy;

import com.example.vote.model.Vote;
import com.example.vote.model.WeightedVote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedPluralityCountingStrategy implements CountingStrategy {

    @Override
    public Map<String, Integer> count(List<Vote> votes) {
        Map<String, Integer> result = new HashMap<>();
        for (var v : votes) {
            int weight = v instanceof WeightedVote wv ? wv.getWeight() : 1;
            result.merge(v.getCandidateId(), weight, Integer::sum);
        }
        return result;
    }
}
