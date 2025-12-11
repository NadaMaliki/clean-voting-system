package com.example.vote.service;

import com.example.vote.model.Vote;
import com.example.vote.repo.VoteRepository;
import com.example.vote.strategy.CountingStrategy;
import com.example.vote.observer.VoteListener;

import java.util.*;

public class VoteService {

    private final VoteRepository repo;
    private final List<VoteListener> listeners = new ArrayList<>();
    private final Set<String> voterIds = new HashSet<>();
    private final Set<String> candidates = new HashSet<>();

    public VoteService(VoteRepository repo) {
        this.repo = repo;
    }

    public void addListener(VoteListener listener) {
        listeners.add(listener);
    }

    public void cast(Vote vote) {
        if (voterIds.contains(vote.getVoterName())) {
            throw new IllegalArgumentException("Voter has already voted: " + vote.getVoterName());
        }
        voterIds.add(vote.getVoterName());
        repo.save(vote);
        for (VoteListener l : listeners) {
            l.onVote(vote);
        }
    }

    public Map<String, Integer> count(CountingStrategy strategy) {
        return strategy.count(repo.findAll());
    }

    public void reset() {
        repo.clear();
    }

    public void addCandidate(String candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(String candidate) {
        candidates.remove(candidate);
        repo.findAll().removeIf(v -> v.getCandidateId().equals(candidate));
    }

    public List<Vote> getHistory() {
        return repo.findAll();
    }

}

