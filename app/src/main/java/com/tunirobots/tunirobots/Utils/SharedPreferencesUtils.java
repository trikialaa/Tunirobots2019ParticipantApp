package com.tunirobots.tunirobots.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tunirobots.tunirobots.BackgroundService;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.Matches.Match;
import com.tunirobots.tunirobots.Features.Matches.MatchsFragment;
import com.tunirobots.tunirobots.Utils.FirebaseClasses.FB_Match;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    private static final String SHARED_PREFERENCES_FILE_TEAMS_INFO = "SHARED_PREFERENCES_FILE_TEAMS_INFO" ;
    private static final String SHARED_PREFERENCES_KEY_TEAMS_INFO = "SHARED_PREFERENCES_KEY_TEAMS_INFO" ;

    public static void saveFollowedTeams(Context activity, ArrayList<Team> teams){

        // Create Gson object.
        Gson gson = new Gson();

        // Get java object list json format string.
        String teamsJsonString = gson.toJson(teams);

        // Create SharedPreferences object.
        Context ctx = activity.getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_TEAMS_INFO, MODE_PRIVATE);

        // Put the json format string to SharedPreferences object.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_TEAMS_INFO, teamsJsonString);
        editor.commit();

        Intent intent = new Intent(activity, BackgroundService.class);
        activity.stopService(intent);
        activity.startService(intent);
    }

    public static ArrayList<Team> loadFollowedTeams(Context activity) {
        // Create SharedPreferences object.
        Context ctx = activity.getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_TEAMS_INFO, MODE_PRIVATE);

        // Get saved string data in it.
        String teamsJsonString = sharedPreferences.getString(SHARED_PREFERENCES_KEY_TEAMS_INFO, "");

        // Create Gson object and translate the json string to related java object array.
        Gson gson = new Gson();
        ArrayList<Team> teams = gson.fromJson(teamsJsonString, new TypeToken<ArrayList<Team>>(){}.getType());
        return teams;
    }


}
