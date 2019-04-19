package com.tunirobots.tunirobots.Features.Matches;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.FollowedTeams.TeamRecyclerViewAdapter;
import com.tunirobots.tunirobots.R;

import java.util.ArrayList;

import jrizani.jrspinner.JRSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchsFragment extends Fragment {


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
        //TODO: add the list of all challenges
        mySpinner.setItems(new String[]{"Challenge 24H","Junior A","Junior B"}); //this is important, you must set it to set the item list

        selectedCompetition = null;

        mySpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedCompetition = mySpinner.getText().toString();
                //TODO: refresh the reyclerview here
            }

        });

        // Checkbox1 : afficher uniquement les équipes suivies
        //TODO: add filter here

        // Checkbox2 : afficher les matchs terminés
        //TODO: add filter here

        // RecyclerView

        new MatchsUpdater().execute();


    }

    public class MatchsUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            matches = new ArrayList<Match>();
            matches.add(new Match("LTCR","Les petits génies de je sais pas quoi","Belja",-1,"A",0));
            matches.add(new Match("LTCR","Les petits génies de je sais pas quoi","Belja",-1,"B",0));
            matches.add(new Match("LTCR","ALAA","Belja",0,null,1));
            matches.add(new Match("LTCR","ALAA","Belja",3,null,2));
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

    //TODO: add the onResume() thing here
}
