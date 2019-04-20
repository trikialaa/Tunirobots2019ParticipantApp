package com.tunirobots.tunirobots;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.tunirobots.tunirobots.Features.FollowedTeams.FollowedTeamsFragment;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.InsatMap.MapFragment;
import com.tunirobots.tunirobots.Features.Matches.MatchsFragment;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        // Actionbar logo

        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        // SharedPreferences fix
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_FILE_TEAMS_INFO", MODE_PRIVATE);
        if (!(sharedPreferences.contains("SHARED_PREFERENCES_KEY_TEAMS_INFO"))){
            SharedPreferencesUtils.saveFollowedTeams(this,new ArrayList<Team>());
        }


        //Fragments

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, new FollowedTeamsFragment(),null);
            fragmentTransaction.commit();
        }


    }
}
