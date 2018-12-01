package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.Model.TeamFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class TeamFrgmentRecyclerAdapter extends RecyclerView.Adapter<TeamFrgmentRecyclerAdapter.ViewHolder>{
    private  String TAG = "RecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<TeamFragmentRecyclerModel> dataList;
    public int curPosition = 0;

    public TeamFrgmentRecyclerAdapter(Context context, int resourceId, List<TeamFragmentRecyclerModel>dataList){
        this.context=context;
        this.resourceId=resourceId;
        this.dataList=dataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        TeamFragmentRecyclerModel recyclerModel = dataList.get(position);
        holder.rankText.setText((recyclerModel.getRank()));
        holder.nameText.setText(recyclerModel.getName());
        holder.gameText.setText(recyclerModel.getGame());
        holder.winText.setText(recyclerModel.getWin());
        holder.loseText.setText(recyclerModel.getLose());
        holder.drawText.setText(recyclerModel.getDraw());
        holder.winningRateText.setText(recyclerModel.getWinningRate());
        holder.gameDisText.setText(recyclerModel.getGameDis());
        holder.recentTenGameText.setText(recyclerModel.getRecentTenGame());
        holder.continuanceText.setText(recyclerModel.getContinuous());
        holder.homeGameText.setText(recyclerModel.getHomeGame());
        holder.expeditionGameText.setText(recyclerModel.getExpeditionGame());


        holder.getListRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                curPosition = holder.getAdapterPosition();
                Log.d(TAG, "curPosition ==> " + curPosition);

            }
        });

    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView rankText;
        TextView nameText;
        TextView gameText;
        TextView winText;
        TextView loseText;
        TextView drawText;
        TextView winningRateText;
        TextView gameDisText;
        TextView recentTenGameText;
        TextView continuanceText;
        TextView homeGameText;
        TextView expeditionGameText;


        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            rankText =itemView.findViewById(R.id.teamRankText);
            nameText =itemView.findViewById(R.id.teamNameText);
            gameText =itemView.findViewById(R.id.teamGameText);
            winText =itemView.findViewById(R.id.teamWinText);
            loseText =itemView.findViewById(R.id.teamLoseText);
            drawText =itemView.findViewById(R.id.teamDrawText);
            winningRateText =itemView.findViewById(R.id.teamWinningRateText);
            gameDisText =itemView.findViewById(R.id.teamGameDisText);
            recentTenGameText =itemView.findViewById(R.id.teamRecentTenGameText);
            continuanceText =itemView.findViewById(R.id.teamContinuanceText);
            homeGameText =itemView.findViewById(R.id.teamHomeGameText);
            expeditionGameText =itemView.findViewById(R.id.teamExpeditionGameText);
            listRoot = itemView.findViewById(R.id.id);
        }
    }


}