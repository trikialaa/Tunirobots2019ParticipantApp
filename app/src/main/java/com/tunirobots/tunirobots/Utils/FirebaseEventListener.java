package com.tunirobots.tunirobots.Utils;

import com.google.firebase.database.ValueEventListener;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;

public class FirebaseEventListener {

    private Team team;
    private ValueEventListener v;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ValueEventListener getV() {
        return v;
    }

    public void setV(ValueEventListener v) {
        this.v = v;
    }

    public FirebaseEventListener(Team team, ValueEventListener v) {
        this.team = team;
        this.v = v;
    }
}
