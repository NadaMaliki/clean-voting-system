package com.example.vote;

import com.example.vote.factory.RepositoryFactory;
import com.example.vote.model.Vote;
import com.example.vote.observer.LoggingVoteListener;
import com.example.vote.repo.VoteRepository;
import com.example.vote.service.VoteService;
import com.example.vote.strategy.PluralityCountingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        VoteRepository repo = RepositoryFactory.createRepository("memory");
        VoteService service = new VoteService(repo);
        service.addListener(new LoggingVoteListener());

        Scanner sc = new Scanner(System.in);

        logger.info("Welcome to Clean VotingApp!");
        logger.info("Commands: vote, count, reset, exit");

        while (true) {
            logger.info("\n> ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "vote" -> {
                    System.out.print("Enter voter name: ");
                    String voter = sc.nextLine();
                    System.out.print("Enter candidate: ");
                    String candidate = sc.nextLine();
                    service.cast(new Vote(voter, candidate, System.currentTimeMillis()));
                }
                case "count" -> {
                    Map<String, Integer> results =
                            service.count(new PluralityCountingStrategy());
                    logger.info("Results: {}", results);
                }
                case "reset" -> {
                    service.reset();
                    logger.info("Reset done!");
                }
                case "exit" -> {
                    logger.info("Bye!");
                    return;
                }
                case "stats" -> {
                    var history = service.getHistory();
                    var count = service.count(new PluralityCountingStrategy());
                    logger.info("Total votes: {}", history.size());
                    logger.info("Top candidates: {}", count);
                }
                default -> logger.warn("Unknown command");
            }
        }
    }
}
