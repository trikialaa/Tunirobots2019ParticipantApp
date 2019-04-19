package com.tunirobots.tunirobots.Features.Matches;

public class Match {

    private String competition;
    private String teamA;
    private String teamB;
    private int nbrMatchsRestants;
    private String gagnant;
    private int round;

    public Match(String competition, String teamA, String teamB, int nbrMatchsRestants, String gagnant, int round) {
        this.competition = competition;
        this.teamA = teamA;
        this.teamB = teamB;
        this.nbrMatchsRestants = nbrMatchsRestants;
        this.gagnant = gagnant;
        this.round = round;
    }

    public Match() {
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
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

    public int getNbrMatchsRestants() {
        return nbrMatchsRestants;
    }

    public void setNbrMatchsRestants(int nbrMatchsRestants) {
        this.nbrMatchsRestants = nbrMatchsRestants;
    }

    public String getGagnant() {
        return gagnant;
    }

    public void setGagnant(String gagnant) {
        this.gagnant = gagnant;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
