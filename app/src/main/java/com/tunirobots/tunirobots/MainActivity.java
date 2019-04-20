package com.tunirobots.tunirobots;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.tunirobots.tunirobots.Features.Contact.ContactFragment;
import com.tunirobots.tunirobots.Features.FollowedTeams.FollowedTeamsFragment;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.InsatMap.MapFragment;
import com.tunirobots.tunirobots.Features.Matches.MatchsFragment;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;
import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

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

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, new ContactFragment(),null);
            fragmentTransaction.commit();
        }

        // Bottom menu

        BottomifyNavigationView bottomify = findViewById(R.id.bottomify_nav);
         bottomify.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
             @Override
             public void onNavigationItemChanged(@NotNull BottomifyNavigationView.NavigationItem navigationItem) {
                 String title = navigationItem.getTextView().getText().toString();
                 if (title.equals("Équipes")){
                     if (findViewById(R.id.fragment)!=null){
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.fragment, new FollowedTeamsFragment(),null);
                         fragmentTransaction.commit();
                     }
                 } else if (title.equals("Matchs")){
                     if (findViewById(R.id.fragment)!=null){
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.fragment, new MatchsFragment(),null);
                         fragmentTransaction.commit();
                     }
                 } else if (title.equals("Carte")){
                     if (findViewById(R.id.fragment)!=null){
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.fragment, new MapFragment(),null);
                         fragmentTransaction.commit();
                     }
                 } else if (title.equals("Contact")){
                     if (findViewById(R.id.fragment)!=null){
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.fragment, new ContactFragment(),null);
                         fragmentTransaction.commit();
                     }
                 }
             }
         });


    }
}
