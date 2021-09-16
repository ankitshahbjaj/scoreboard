package com.phonepe.scorecard.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Player {
    private String name;
    private int runsScored;
    private int fours;
    private int sixes;
    private int ballsFaced;
    private BigDecimal strikeRate;
    private BigDecimal oversBalled;
    private int runsConceded;
    private int maidenOvers;
    private int dotBalls;
    private int wicketsTaken;
    private BigDecimal economy;
    private boolean onCrease;


    public Player(String name) {
        this.name = name;
        this.runsScored = 0;
        this.fours = 0;
        this.sixes = 0;
        this.ballsFaced = 0;
        this.strikeRate = BigDecimal.ZERO;
        this.oversBalled = BigDecimal.ZERO;
        this.runsConceded = 0;
        this.maidenOvers = 0;
        this.dotBalls = 0;
        this.wicketsTaken = 0;
        this.economy = BigDecimal.ZERO;
        this.onCrease = false;
    }


    public void updateRuns(int runs) {
        this.runsScored += runs;
        checkBoundary(runs);
        increaseBall();
    }

    public void checkBoundary(int runs) {
        if(runs == 4)
            this.fours++;
        else if(runs == 6)
            this.sixes++;
    }

    public void increaseBall() {
        this.ballsFaced += 1;
        this.calculateStrikeRate();
    }

    public void calculateStrikeRate() {
        if(this.runsScored > 0) {
            this.strikeRate = BigDecimal.valueOf(this.runsScored)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(this.ballsFaced), 2, RoundingMode.HALF_DOWN);
        }
    }
}
