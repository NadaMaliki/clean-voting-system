package com.example.vote.strategy;

import com.example.vote.model.Vote;

import java.util.List;
import java.util.Map;

public interface CountingStrategy {
    Map<String, Integer> count(List<Vote> votes);
}
