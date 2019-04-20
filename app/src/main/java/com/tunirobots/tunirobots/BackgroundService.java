package com.tunirobots.tunirobots;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Utils.BackgroundUtils;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class BackgroundService extends Service {

    Context context;
    Thread thread;

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
               BackgroundUtils.subscribeToTeams(teams,context);
           }
       });

       thread.start();
        Log.e("Thread","Started");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
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


