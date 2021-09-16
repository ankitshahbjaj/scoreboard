package com.phonepe.scorecard.model;

import lombok.Data;

@Data
public class Match {
    private Team team1;
    private Team team2;
    private Team winner;
    private String result;
    private int numberOfOver;
    private int target;

    public Match(int numberOfOver, Team team1, Team team2) {
        this.numberOfOver = numberOfOver;
        this.team1 = team1;
        this.team2 = team2;
        this.target = -1;
    }
}
