package com.tunirobots.tunirobots.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.R;
import com.tunirobots.tunirobots.Utils.FirebaseClasses.FB_Match;

import java.util.ArrayList;
import java.util.Random;

public class BackgroundUtils {

    public static FirebaseEventListener subscribeToTeam(final Team team, final Context context){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String comp = team.getCompetition();
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
        Log.e("TEST","made it to rounds");
        ValueEventListener v = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("TEST","Starting data fetching");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    int round = Integer.parseInt(""+(postSnapshot.getKey().charAt(postSnapshot.getKey().length()-1)));
                    Log.e("TEST","round "+round);
                    for (DataSnapshot postSnapshot2: postSnapshot.getChildren()) {
                        FB_Match fb_match = postSnapshot2.getValue(FB_Match.class);
                        Log.e("TEST",fb_match.toString());
                        if (fb_match.getRemaining()==0){
                            if ((fb_match.getTeamA().equals(team.getName()))||(fb_match.getTeamB().equals(team.getName()))){
                                //TESTING
                                ArrayList<Team> all = SharedPreferencesUtils.loadFollowedTeams(context);
                                for (Team t: all) Log.e("NOTIF",t.toString());
                                Log.e("NOTIF","SEND THE NOTIFICATION - "+team.getName());
                                /////////////////////////////////////////////
                                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context,"channel")
                                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                                        .setSmallIcon(R.drawable.tr_icon_white)
                                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.tr_with_bg))
                                        .setContentTitle(team.getName())
                                        .setContentText("Vous avez un match maintenant !");

                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                {
                                    String channelId = "Your_channel_id";
                                    NotificationChannel channel = new NotificationChannel(channelId,
                                            "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
                                    notificationManager.createNotificationChannel(channel);
                                    notificationBuilder.setChannelId(channelId);
                                }
                                Random r = new Random();
                                int i1 = r.nextInt(200);
                                notificationManager.notify(i1,notificationBuilder.build());

                                /////////////////////////////////////////////
                            }

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TEST","There's a cancelled problem");
            }

        };
        mRounds.addValueEventListener(v);

        return new FirebaseEventListener(team,v);
    }

    public ArrayList<FirebaseEventListener> subscribeToTeams(ArrayList<Team> teams,Context context){
        ArrayList<FirebaseEventListener> listeners = new ArrayList<>();
        for (Team team : teams) listeners.add(subscribeToTeam(team,context));
        return listeners;
    }
}
