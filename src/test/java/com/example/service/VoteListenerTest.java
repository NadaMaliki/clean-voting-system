package com.example.service;

import com.example.vote.model.Vote;
import com.example.vote.observer.AuditVoteListener;
import com.example.vote.service.VoteService;
import com.example.vote.repo.InMemoryVoteRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoteListenerTest {

    @Test
    void testListenerCalled() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        AtomicBoolean called = new AtomicBoolean(false);

        service.addListener(v -> called.set(true));

        service.cast(new Vote("v1", "Alice", 1L));

        assertTrue(called.get(), "Listener should have been called");
    }

    @Test
    void testAuditListener() throws IOException {
        File temp = File.createTempFile("audit", ".csv");
        temp.deleteOnExit();

        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.addListener(new AuditVoteListener(temp.getAbsolutePath()));
        svc.cast(new Vote("v1", "Alice", 1L));

        List<String> lines = Files.readAllLines(temp.toPath());
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("Alice"));
    }

}

