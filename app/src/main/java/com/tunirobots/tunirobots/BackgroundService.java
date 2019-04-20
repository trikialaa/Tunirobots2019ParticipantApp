package com.tunirobots.tunirobots;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Utils.BackgroundUtils;
import com.tunirobots.tunirobots.Utils.FirebaseEventListener;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class BackgroundService extends Service {

    Context context;
    Thread thread;
    ArrayList<FirebaseEventListener> listeners;

    public BackgroundService() {
        context=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_FILE_TEAMS_INFO", MODE_PRIVATE);
        if (!(sharedPreferences.contains("SHARED_PREFERENCES_KEY_TEAMS_INFO"))){
            SharedPreferencesUtils.saveFollowedTeams(this,new ArrayList<Team>());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       thread = new Thread(new Runnable() {
           @Override
           public void run() {
               ArrayList<Team> teams = SharedPreferencesUtils.loadFollowedTeams(context);
               BackgroundUtils backgroundUtils = new BackgroundUtils();
               listeners = backgroundUtils.subscribeToTeams(teams,context);
           }
       });

       thread.start();
        Log.e("Thread","Started");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        for (FirebaseEventListener listener : listeners) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            String comp = listener.getTeam().getCompetition();
            String childName = "";

            if (comp.equals("Challenge 24H")){
                childName="24h";
            } else if (comp.equals("Gadget Challenge")){
                childName="gadget";
            } else if (comp.equals("Junior A")){
                childName="juniorA";
            } else if (comp.equals("Junior B")){
                childName="juniorB";
            } else if (comp.equals("LTRC")){
                childName="ltcr";
            } else if (comp.equals("Sumo Challenge")){
                childName="sumo";
            }

            DatabaseReference mRounds = mDatabase.child(childName).child("rounds");
            mRounds.removeEventListener(listener.getV());
        }
        thread.interrupt();
        super.onDestroy();
        Log.e("Thread","Destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


