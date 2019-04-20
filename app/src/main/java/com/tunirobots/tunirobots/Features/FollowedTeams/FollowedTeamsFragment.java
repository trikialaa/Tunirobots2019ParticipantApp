package com.tunirobots.tunirobots.Features.FollowedTeams;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunirobots.tunirobots.R;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowedTeamsFragment extends Fragment {

    private ArrayList<Team> teams;
    private JRSpinner mySpinner,mySpinner2;
    private String selectedCompetition,selectedTeam;
    private TeamRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followed_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Competition Spinner :

        mySpinner = getActivity().findViewById(R.id.spn_my_spinner);
        //TODO: add the list of all challenges
        mySpinner.setItems(new String[]{"Challenge 24H","Gadget Challenge","Junior A","Junior B","LTRC","Sumo Challenge"});

        selectedCompetition = null;
        selectedTeam = null;

        mySpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedCompetition = mySpinner.getText().toString();
                new TeamNamesUpdater().execute();
            }

        });

        // Equipe Spinner :

        mySpinner2 = getActivity().findViewById(R.id.spn_my_spinner2);

        mySpinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCompetition==null) Toast.makeText(getActivity().getApplicationContext(),"Veuillez sélectionner une compétition",Toast.LENGTH_SHORT).show();

            }
        });

        mySpinner2.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedTeam = mySpinner2.getText().toString();
                if (teamInList(selectedCompetition,selectedTeam,teams)){
                    Toast.makeText(getActivity().getApplicationContext(),"Cette équipe est déja suivie",Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.addItem(new Team(selectedCompetition,selectedTeam),teams.size());
                }
                Log.e("TEST",selectedTeam+" "+selectedCompetition);
                for (Team t: teams) Log.e("TEST",t.toString());
            }

        });

        // RecyclerView

        new FollowedTeamsFragment.FollowedTeamsUpdater().execute();


    }

    public Boolean teamInList(String comp,String team,ArrayList<Team> teams){
        for (Team t : teams){
            if ((t.getCompetition().equals(comp))&&(t.getName().equals(team))) return true;
        }
        return false;
    }

    public class FollowedTeamsUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            teams = SharedPreferencesUtils.loadFollowedTeams(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new TeamRecyclerViewAdapter(getActivity(),teams);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

        }
    }

    public class TeamNamesUpdater extends AsyncTask<Void,Void,Void> {

        ArrayList<String> teamNames;
        String[] teamNamesArray;

        @Override
        protected Void doInBackground(Void... params) {
            teamNames = new ArrayList<>();
            String comp = selectedCompetition;
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
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

            DatabaseReference mTeams = mDatabase.child(childName).child("teams");
            mTeams.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        teamNames.add(postSnapshot.getKey());
                        Log.e("TEST",postSnapshot.getKey());
                    }
                    teamNamesArray = teamNames.toArray(new String[teamNames.size()]);
                    mySpinner2.setItems(teamNamesArray);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("The read failed: " ,databaseError.toString());
                }

            });



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    //TODO: add onResume thing


}
