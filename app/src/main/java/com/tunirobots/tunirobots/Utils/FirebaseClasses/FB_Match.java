package com.tunirobots.tunirobots.Utils.FirebaseClasses;

public class FB_Match {

    private String teamA;
    private String teamB;
    private String winner;

    public FB_Match() {
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
