package com.example.vote.model;

public class Vote {

    private String voterName;
    private String candidateId;
    private long timestamp;

    public Vote(String voterName, String candidateId, long timestamp) {
        this.voterName = voterName;
        this.candidateId = candidateId;
        this.timestamp = timestamp;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voterName='" + voterName + '\'' +
                ", candidateId='" + candidateId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
