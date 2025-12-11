package com.example.vote.observer;

import com.example.vote.model.Vote;
import java.io.FileWriter;
import java.io.IOException;

public class AuditVoteListener implements VoteListener {

    private final String filename;

    public AuditVoteListener(String filename) {
        this.filename = filename;
    }

    @Override
    public void onVote(Vote vote) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(vote.getVoterName() + "," + vote.getCandidateId() + "," + vote.getTimestamp() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
