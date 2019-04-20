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

import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.Features.FollowedTeams.TeamRecyclerViewAdapter;
import com.tunirobots.tunirobots.R;
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
        //TODO: load list of all challenges from Firebase
        mySpinner.setItems(new String[]{"Challenge 24H","Gadget Challenge","Junior A","Junior B","LTRC","Sumo Challenge"}); //this is important, you must set it to set the item list

        selectedCompetition = null;

        mySpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback

            @Override
            public void onItemClick(int position) {
                selectedCompetition = mySpinner.getText().toString();
                if (selectedCompetition != null){
                    FinalFilter f = new FinalFilter();
                    matches = f.filter(unfilteredMatches);
                    mAdapter.changeItems(matches);
                    mAdapter.notifyDataSetChanged();
                }
            }

        });

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
        unfilteredMatches.add(new Match("Challenge 24H","Equipe A","Equipe B",-1,"A",0));
        unfilteredMatches.add(new Match("Challenge 24H","Equipe A","Belja",-1,"B",0));
        unfilteredMatches.add(new Match("Challenge 24H","ALAA","Equipe B",0,null,1));
        unfilteredMatches.add(new Match("Challenge 24H","ALAA","Belja",3,null,2));
        matches=MatchesFilters.removeFinishedMatches(unfilteredMatches);
        for (Match m : unfilteredMatches) Log.e("TEST",String.valueOf(m.getRound()));
        for (Match m : matches) Log.e("TEST",String.valueOf(m.getRound()));

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

    //TODO: add the onResume() thing here
}
