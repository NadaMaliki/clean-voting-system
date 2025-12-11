package com.example.vote.repo;

import com.example.vote.model.Vote;
import java.util.List;

public interface VoteRepository {
    void save(Vote vote);
    List<Vote> findAll();
    void clear();
    void removeVotesFor(String candidateId);
}