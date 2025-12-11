package com.example.vote;

import com.example.vote.factory.RepositoryFactory;
import com.example.vote.model.Vote;
import com.example.vote.observer.LoggingVoteListener;
import com.example.vote.repo.VoteRepository;
import com.example.vote.service.VoteService;
import com.example.vote.strategy.PluralityCountingStrategy;

import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        VoteRepository repo = RepositoryFactory.createRepository("memory");
        VoteService service = new VoteService(repo);
        service.addListener(new LoggingVoteListener());

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Clean VotingApp!");
        System.out.println("Commands: vote, count, reset, exit");

        while (true) {
            System.out.print("> ");
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
                    System.out.println("Results: " + results);
                }
                case "reset" -> {
                    service.reset();
                    System.out.println("Reset done!");
                }
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                case "stats" -> {
                    var history = service.getHistory();
                    var count = service.count(new PluralityCountingStrategy());
                    System.out.println("Total votes: " + history.size());
                    System.out.println("Top candidates: " + count);
                }
                default -> System.out.println("Unknown command");
            }
        }
    }
}
