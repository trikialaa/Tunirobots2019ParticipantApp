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

import com.tunirobots.tunirobots.R;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

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
        mySpinner.setItems(new String[]{"Challenge 24H","Junior A","Junior B"}); //this is important, you must set it to set the item list

        selectedCompetition = null;
        selectedTeam = null;

        mySpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedCompetition = mySpinner.getText().toString();
                //TODO: add the list of all teams accoring to challenge
                mySpinner2.setItems(new String[]{"Equipe 1","Equipe 2","Equipe 3"});
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
            teams = new ArrayList<Team>();
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

    //TODO: add onResume thing


}
