package com.tunirobots.tunirobots.Features.Matches;


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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.FollowedTeams.TeamRecyclerViewAdapter;
import com.tunirobots.tunirobots.R;
import com.tunirobots.tunirobots.Utils.FirebaseClasses.FB_Match;
import com.tunirobots.tunirobots.Utils.MatchesFilters;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchsFragment extends Fragment {


    private ArrayList<Match> unfilteredMatches;
    private ArrayList<Match> matches;
    private JRSpinner mySpinner;
    private CheckBox cb1,cb2;
    private String selectedCompetition;
    private MatchRecyclerViewAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matchs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Competition Spinner :

        mySpinner = getActivity().findViewById(R.id.spn_my_spinner);
        mySpinner.setItems(new String[]{"Challenge 24H","Gadget Challenge","Junior A","Junior B","LTRC","Sumo Challenge"}); //this is important, you must set it to set the item list

        selectedCompetition = null;

        mySpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedCompetition = mySpinner.getText().toString();
                Log.e("TEST","Executing match updating");
                new MatchListUpdater().execute();
                if (selectedCompetition != null){
                    FinalFilter f = new FinalFilter();
                    matches = f.filter(unfilteredMatches);
                    mAdapter.changeItems(matches);
                    mAdapter.notifyDataSetChanged();
                }
            }

        });

        //TODO: Save settings to SharedPreferences

        // Checkbox1 : afficher uniquement les équipes suivies
        cb1=getActivity().findViewById(R.id.cb1);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectedCompetition != null){
                    FinalFilter f = new FinalFilter();
                    matches = f.filter(unfilteredMatches);
                    mAdapter.changeItems(matches);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        // Checkbox2 : afficher les matchs terminés
        cb2=getActivity().findViewById(R.id.cb2);
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectedCompetition != null){
                    FinalFilter f = new FinalFilter();
                    matches = f.filter(unfilteredMatches);
                    mAdapter.changeItems(matches);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        // RecyclerView

        unfilteredMatches = new ArrayList<Match>();
        matches=MatchesFilters.removeFinishedMatches(unfilteredMatches);

        new MatchsUpdater().execute();


    }

    public class FinalFilter{
        public ArrayList<Match> filter(ArrayList<Match> rawMatches){
            ArrayList<Match> result = (ArrayList<Match>)rawMatches.clone();
            if (cb1.isChecked()){
                if (cb2.isChecked()){
                    result = MatchesFilters.removeUnfollowedTeams(getActivity(),selectedCompetition,result);
                } else {
                    result = MatchesFilters.removeUnfollowedTeams(getActivity(),selectedCompetition,result);
                    result = MatchesFilters.removeFinishedMatches(result);
                }
            } else {
                if (cb2.isChecked()){
                    // Do Nothing
                } else {
                    result = MatchesFilters.removeFinishedMatches(result);
                }
            }
            return result;
        }
    }

    public class MatchsUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new MatchRecyclerViewAdapter(getActivity(),matches);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

    public class MatchListUpdater extends AsyncTask<Void,Void,Void> {

        ArrayList<Match> rawMatches;

        @Override
        protected Void doInBackground(Void... params) {
            rawMatches = new ArrayList<>();
            final String comp = selectedCompetition;
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            String childName = "";


            Log.e("TEST","Selection phase");
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

            DatabaseReference mTeams = mDatabase.child(childName).child("rounds");
            Log.e("TEST","made it to rounds");
            mTeams.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    rawMatches = new ArrayList<>();
                    Log.e("TEST","Starting data fetching");
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        int round = Integer.parseInt(""+(postSnapshot.getKey().charAt(postSnapshot.getKey().length()-1)));
                        Log.e("TEST","round "+round);
                        for (DataSnapshot postSnapshot2: postSnapshot.getChildren()) {
                            FB_Match fb_match = postSnapshot2.getValue(FB_Match.class);
                            Log.e("TEST",fb_match.toString());
                            Match rawMatch = new Match(comp,fb_match.getTeamA(),fb_match.getTeamB(),(fb_match.getRemaining()).intValue(),fb_match.getWinner(),round);
                            rawMatches.add(rawMatch);
                    }

                }
                    unfilteredMatches = rawMatches;
                    FinalFilter f = new FinalFilter();
                    matches = f.filter(unfilteredMatches);
                    mAdapter.changeItems(matches);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("TEST","There's a cancelled problem");
                }

            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("TEST","Post execute running too soon ?");
        }
    }

    //TODO: add the onResume() thing here
}
