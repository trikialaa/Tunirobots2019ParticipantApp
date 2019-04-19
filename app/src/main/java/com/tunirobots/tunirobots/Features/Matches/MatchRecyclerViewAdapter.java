package com.tunirobots.tunirobots.Features.Matches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tunirobots.tunirobots.Features.FollowedTeams.Team;
import com.tunirobots.tunirobots.R;

import java.util.ArrayList;

public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.Viewholder> {


    private Context context;
    private ArrayList<Match> matches;

    public MatchRecyclerViewAdapter(Context context, ArrayList<Match> matches) {
        this.context = context;
        this.matches=matches;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match,parent,false);
        Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchRecyclerViewAdapter.Viewholder holder, final int position) {
        Match match =matches.get(position);
        holder.round.setText("Round "+match.getRound());
        holder.equipeA.setText(match.getTeamA());
        holder.equipeB.setText(match.getTeamB());
        if (match.getNbrMatchsRestants()==(-1)){
            if (match.getGagnant().equals("A")){
                holder.equipeA.setTextColor(context.getApplicationContext().getResources().getColor(R.color.green));
                holder.equipeB.setTextColor(context.getApplicationContext().getResources().getColor(R.color.red));
                holder.statut.setText("L'équipe "+match.getTeamA()+" a remporté le match");
            } else {
                holder.equipeB.setTextColor(context.getApplicationContext().getResources().getColor(R.color.green));
                holder.equipeA.setTextColor(context.getApplicationContext().getResources().getColor(R.color.red));
                holder.statut.setText("L'équipe "+match.getTeamB()+" a remporté le match");
            }
        } else if (match.getNbrMatchsRestants()==0){
            holder.statut.setText("Match en cours");
        } else {
            holder.statut.setText(match.getNbrMatchsRestants()+" matches restants");
        }

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView round,equipeA,equipeB,statut;

        public Viewholder(View itemView) {
            super(itemView);
            round = itemView.findViewById(R.id.textView3);
            equipeA = itemView.findViewById(R.id.textView4);
            equipeB = itemView.findViewById(R.id.textView7);
            statut = itemView.findViewById(R.id.textView8);
        }

    }

    private void deleteItem(int position) {
        matches.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,matches.size());
    }

    public void addItem(Match match, int position) {
        matches.add(position, match);
        notifyItemInserted(position);
    }

    public void changeItems(ArrayList<Match> matches) {
        this.matches=matches;
    }
}
