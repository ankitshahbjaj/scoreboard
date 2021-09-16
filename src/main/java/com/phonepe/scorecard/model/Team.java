package com.phonepe.scorecard.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {
    private String teamName;
    private int numberOfPlayers;
    private List<Player> playerList;
    private int totalScore;
    private int wickets;
    private int extraRuns;
    private Player striker;
    private Player nonStriker;
    private int nextPlayer;
    private OverCounter overCounter;

    public Team(String teamName, int numberOfPlayers) {
        this.teamName = teamName;
        this.numberOfPlayers = numberOfPlayers;
        this.playerList = new ArrayList<>();
        this.totalScore = 0;
        this.wickets = 0;
        this.extraRuns = 0;
        this.overCounter = new OverCounter();
    }

    public void startInnings() {
        if(numberOfPlayers < 2)
            throw new IllegalStateException("At least 2 Players are required to start innings");

        this.striker = playerList.get(0);
        striker.setOnCrease(true);
        this.nonStriker = playerList.get(1);
        nonStriker.setOnCrease(true);
        if(numberOfPlayers > 2) {
            this.nextPlayer = 2;
        } else {
            this.nextPlayer = -1;
        }
    }

    public boolean isAllOut() {
        return (this.wickets + 1) == this.numberOfPlayers;
    }

    public void handleWicketFall(int ballNumber) {
        striker.setOnCrease(false);
        if(nextPlayer == -1) {
            Player striker = null;
        } else {
            striker = playerList.get(nextPlayer);
            striker.setOnCrease(true);
            if(nextPlayer + 1 < numberOfPlayers) {
                nextPlayer++;
            } else {
                nextPlayer = -1;
            }
        }
        this.wickets = this.wickets + 1;
        overCounter.increaseBall();
    }

    public void handleWideBall() {
        this.setTotalScore(this.getTotalScore()+1);
        this.setExtraRuns(this.getExtraRuns()+1);
    }

    public void handleNoBall() {
        this.setTotalScore(this.getTotalScore()+1);
        this.setExtraRuns(this.getExtraRuns()+1);
        striker.increaseBall();
    }

    public void handleRunScored(String runs, int ballNumber) {
        int runsScored = Integer.parseInt(runs);
        if(runsScored < 0 || runsScored > 6)
            throw new IllegalStateException("Runs can not be lesser than 0 and more than 6");

        striker.updateRuns(runsScored);
        this.totalScore = this.totalScore + runsScored;
        if(runsScored % 2 == 1) {
            swapStrike();
        }
        overCounter.increaseBall();
    }

    public void swapStrike() {
        Player temp = striker;
        striker = nonStriker;
        nonStriker = temp;
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }
}
