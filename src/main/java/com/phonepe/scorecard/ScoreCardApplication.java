package com.phonepe.scorecard;

import com.phonepe.scorecard.handler.CricketMatchHandler;
import com.phonepe.scorecard.model.Match;
import com.phonepe.scorecard.model.Player;
import com.phonepe.scorecard.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreCardApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CricketMatchHandler handler = new CricketMatchHandler();

        System.out.println("Enter First Team Name : ");
        String team1Name = scanner.nextLine();

        System.out.println("Enter Second Team Name : ");
        String team2Name = scanner.nextLine();

        System.out.println("No. of players for each team : ");
        int playerCount = scanner.nextInt();

        System.out.println("No. of overs : ");
        int overCount = scanner.nextInt();

        Match match = handler
                .startMatch(overCount, playerCount, team1Name, team2Name);

        handler.playInnings(match.getTeam1());
        handler.playInnings(match.getTeam2());
        handler.calculateWinner();
        System.out.println(match.getResult());
    }

}
