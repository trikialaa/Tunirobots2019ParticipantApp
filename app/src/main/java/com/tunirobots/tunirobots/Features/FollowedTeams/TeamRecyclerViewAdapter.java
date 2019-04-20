package com.tunirobots.tunirobots.Features.FollowedTeams;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tunirobots.tunirobots.R;
import com.tunirobots.tunirobots.Utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.Viewholder> {


    private Context context;
    private ArrayList<Team> teams;

    public TeamRecyclerViewAdapter(Context context, ArrayList<Team> teams) {
        this.context = context;
        this.teams = teams;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);
        Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRecyclerViewAdapter.Viewholder holder, final int position) {
        Team team =teams.get(position);
        holder.equipe.setText(team.toString());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView equipe;
        Button del;

        public Viewholder(View itemView) {
            super(itemView);
            equipe = itemView.findViewById(R.id.textView5);
            del = itemView.findViewById(R.id.button3);


        }

    }

    private void deleteItem(int position) {
        teams.remove(position);
        SharedPreferencesUtils.saveFollowedTeams((Activity)context,teams);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,teams.size());
    }

    public void addItem(Team team, int position) {
        teams.add(position, team);
        SharedPreferencesUtils.saveFollowedTeams((Activity)context,teams);
        notifyItemInserted(position);
    }
}
