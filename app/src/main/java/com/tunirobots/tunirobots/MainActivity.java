package com.tunirobots.tunirobots;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tunirobots.tunirobots.Features.FollowedTeams.FollowedTeamsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Actionbar logo

        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);


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
