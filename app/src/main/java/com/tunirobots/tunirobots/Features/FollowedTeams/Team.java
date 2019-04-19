package com.tunirobots.tunirobots.Features.FollowedTeams;

public class Team {

    private String competition;
    private String name;


    public Team(String competition, String name) {
        this.name = name;
        this.competition = competition;
    }

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "["+competition+"] "+name;
    }
}
