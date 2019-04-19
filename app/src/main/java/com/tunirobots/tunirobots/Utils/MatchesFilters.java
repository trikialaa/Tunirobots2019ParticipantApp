package com.tunirobots.tunirobots.Utils;

import android.app.Activity;

import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.Matches.Match;

import java.util.ArrayList;

public class MatchesFilters {

    public static ArrayList<Match> removeFinishedMatches(ArrayList<Match> matches){
        ArrayList<Match> tmp = (ArrayList<Match>)matches.clone();
        ArrayList<Match> toRemove = new ArrayList<>();
        for (Match match : matches){
            if (match.getGagnant()!=null) toRemove.add(match);
        }
        tmp.removeAll(toRemove);
        return tmp;
    }

    public static ArrayList<Match> removeUnfollowedTeams(Activity activity, String comp, ArrayList<Match> matches){
        ArrayList<Match> tmp = (ArrayList<Match>)matches.clone();
        ArrayList<Team> followedTeams = SharedPreferencesUtils.loadFollowedTeams(activity);
        ArrayList<Team> teamsToRemove = new ArrayList<>();
        for (Team team : followedTeams){
            if (!(team.getCompetition().equals(comp))) teamsToRemove.add(team);
        }
        followedTeams.removeAll(teamsToRemove);
        ArrayList<String> followedTeamsNames = new ArrayList<>();
        for (Team team : followedTeams) followedTeamsNames.add(team.getName());
        ArrayList<Match> toRemove = new ArrayList<>();
        for (Match match : matches){
            if (!((followedTeamsNames.contains(match.getTeamA()))||(followedTeamsNames.contains(match.getTeamB())))) toRemove.add(match);
        }
        tmp.removeAll(toRemove);
        return tmp;
    }
}
