package com.example.vote.repo;

import com.example.vote.model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcVoteRepository implements VoteRepository {

    private final String url = "jdbc:h2:./votesdb"; // fichier local votesdb.mv.db
    private final String user = "sa";
    private final String password = "";

    public JdbcVoteRepository() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS votes (
                    voter_name VARCHAR(255) PRIMARY KEY,
                    candidate_id VARCHAR(255),
                    timestamp BIGINT
                )
            """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize DB", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void save(Vote vote) {
        String sql = "MERGE INTO votes (voter_name, candidate_id, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vote.getVoterName());
            ps.setString(2, vote.getCandidateId());
            ps.setLong(3, vote.getTimestamp());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save vote", e);
        }
    }

    @Override
    public List<Vote> findAll() {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT voter_name, candidate_id, timestamp FROM votes";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                votes.add(new Vote(
                        rs.getString("voter_name"),
                        rs.getString("candidate_id"),
                        rs.getLong("timestamp")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read votes", e);
        }
        return votes;
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM votes";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear votes", e);
        }
    }

    @Override
    public void removeVotesFor(String candidateId) {
        String sql = "DELETE FROM votes WHERE candidate_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, candidateId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove votes for candidate: " + candidateId, e);
        }
    }

}
