package com.example.vote.model;

public class WeightedVote extends Vote {
    private final int weight;

    public WeightedVote(String voterName, String candidateId, long timestamp, int weight) {
        super(voterName, candidateId, timestamp);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
