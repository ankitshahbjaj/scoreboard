package com.phonepe.scorecard.model;

import static com.phonepe.scorecard.constant.Constant.BALLS_IN_OVER;

public class OverCounter {
    private int overs;
    private int balls;

    public OverCounter() {
        this.overs = 0;
        this.balls = 0;
    }

    public void increaseBall() {
        this.balls++;
        if(balls == BALLS_IN_OVER) {
            this.overs++;
            this.balls = 0;
        }
    }

    public String getOversCounter() {
        return this.overs + "." + this.balls;
    }
}
