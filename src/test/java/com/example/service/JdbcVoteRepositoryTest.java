package com.example.service;

import com.example.vote.model.Vote;
import com.example.vote.repo.JdbcVoteRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcVoteRepositoryTest {

    private JdbcVoteRepository repo;

    @BeforeEach
    void setup() {
        repo = new JdbcVoteRepository();
        repo.clear();
    }

    @Test
    void testSaveAndFindAll() {
        Vote v1 = new Vote("v1", "Alice", 1L);
        Vote v2 = new Vote("v2", "Bob", 2L);

        repo.save(v1);
        repo.save(v2);

        List<Vote> votes = repo.findAll();
        assertEquals(2, votes.size());
        assertEquals("Alice", votes.get(0).getCandidateId());
    }

    @Test
    void testClear() {
        repo.save(new Vote("v1", "Alice", 1L));
        repo.clear();
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void testDuplicateVote() {
        repo.save(new Vote("v1", "Alice", 1L));
        repo.save(new Vote("v1", "Bob", 2L)); // MERGE INTO devrait remplacer Alice par Bob
        List<Vote> votes = repo.findAll();
        assertEquals(1, votes.size());
        assertEquals("Bob", votes.get(0).getCandidateId());
    }
}

