package com.phonepe.scorecard.handler;

import com.phonepe.scorecard.model.Match;
import com.phonepe.scorecard.model.Player;
import com.phonepe.scorecard.model.Team;

import java.util.Scanner;

import static com.phonepe.scorecard.constant.Constant.*;

public class CricketMatchHandler {

    Match match;

    public Match startMatch(int numberOfOvers, int numberOfPlayers, String team1Name, String team2Name) {
        Team team1 = new Team(team1Name, numberOfPlayers);
        Team team2 = new Team(team2Name, numberOfPlayers);

        this.match = new Match(numberOfOvers, team1, team2);
        return this.match;
    }


    public void playInnings(Team team) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Batting order for team : " + team.getTeamName());
        for(int i = 0; i < team.getNumberOfPlayers(); i++) {
            String name = scanner.nextLine();
            Player player = new Player(name);
            team.addPlayer(player);
        }

        team.startInnings();

        for(int i = 1 ; i <= match.getNumberOfOver(); i++) {
            System.out.println("Over : " + i);
            int count = 1;

            while(count <= BALLS_IN_OVER) {
                String ballResult = scanner.nextLine();
                switch (ballResult) {
                    case WICKET :
                        team.handleWicketFall(count);
                        count++;
                        break;
                    case WIDE :
                        team.handleWideBall();
                        break;
                    case NO_BALL :
                        team.handleNoBall();
                        break;
                    default:
                        team.handleRunScored(ballResult, count);
                        count++;
                }
                if(ballResult.equals(WICKET)) {
                    if(team.isAllOut()) break;
                } else if (match.getTarget() != -1 && team.getTotalScore() > match.getTarget()) {
                    break;
                }
            }
            printStatus(team);
            if(team.isAllOut()) {
                break;
            }else if (match.getTarget() != -1 && team.getTotalScore() > match.getTarget()) {
                break;
            }
            team.swapStrike();
        }
        if(match.getTarget() == -1) {
            match.setTarget(team.getTotalScore());
        }
    }

    public void printStatus(Team team) {
        System.out.println();
        System.out.printf("ScoreCard for team : %s%n", team.getTeamName());
        System.out.format("%-20s%-10s%-10s%-10s%-10s%-10s%n", PLAYER_NAME, SCORE, FOURS, SIXES, BALLS, STRIKE_RATE);
        team.getPlayerList().forEach(this::printStatusLineItem);
        System.out.println("Total : " + team.getTotalScore() + "/" + team.getWickets() + " (Extras : " + team.getExtraRuns() + ")");
        System.out.println("Overs : " + team.getOverCounter().getOversCounter());
        System.out.println();
    }

    public void printStatusLineItem(Player p) {
        System.out.format("%-20s%-10d%-10d%-10d%-10d%-10g%n", p.isOnCrease() ? p.getName()+"*" :p.getName(),
                p.getRunsScored(), p.getFours(), p.getSixes(), p.getBallsFaced(), p.getStrikeRate());
    }

    public void calculateWinner() {
        if(match.getTeam1().getTotalScore() > match.getTeam2().getTotalScore()) {
            Team winner = match.getTeam1();
            int diff = winner.getTotalScore() - match.getTeam2().getTotalScore();
            match.setWinner(winner);
            match.setResult(String.format("Team %s won by %d runs", winner.getTeamName(), diff));
        } else if (match.getTeam2().getTotalScore() > match.getTeam1().getTotalScore()) {
            Team winner = match.getTeam2();
            int diff = winner.getNumberOfPlayers() - winner.getWickets();
            match.setWinner(winner);
            match.setResult(String.format("Team %s won by %d wickets", winner.getTeamName(), diff));
        } else {
            match.setResult("Match Drawn");
        }
    }
}
