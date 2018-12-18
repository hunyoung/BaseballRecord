package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.PlayerFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerFragmentRecyclerAdapter extends RecyclerView.Adapter<PlayerFragmentRecyclerAdapter.ViewHolder>{
    private  String TAG = "RecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<PlayerFragmentRecyclerModel> dataList;
    public int curPosition = 0;

    public PlayerFragmentRecyclerAdapter(Context context, int resourceId, List<PlayerFragmentRecyclerModel>dataList){
        this.context=context;
        this.resourceId=resourceId;
        this.dataList=dataList;

    }

    public void listInit(){
        Log.d(TAG, "listInit()");
        dataList = new ArrayList<>();
        dataList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        PlayerFragmentRecyclerModel recyclerModel = dataList.get(position);

        holder.colum.setText(recyclerModel.getColum());
        holder.year.setText(recyclerModel.getYear());
        holder.teamName.setText(recyclerModel.getTeamName());
        holder.age.setText(recyclerModel.getAge());
        holder.position.setText(recyclerModel.getPosition());
        holder.game.setText(recyclerModel.getGame());
        holder.battingCount.setText(recyclerModel.getBattingCount());
        holder.realBattingCount.setText(recyclerModel.getRealBattingCount());
        holder.round.setText(recyclerModel.getRound());
        holder.oneBase.setText(recyclerModel.getOneBase());
        holder.twoBase.setText(recyclerModel.getTwoBase());
        holder.threeBase.setText(recyclerModel.getThreeBase());
        holder.homeRun.setText(recyclerModel.getHomeRun());
        holder.totalBase.setText(recyclerModel.getTotalBase());
        holder.runBattedIn.setText(recyclerModel.getRunBattedIn());
        holder.steal.setText(recyclerModel.getSteal());
        holder.stealFail.setText(recyclerModel.getStealFail());
        holder.fourBall.setText(recyclerModel.getFourBall());
        holder.hittedBall.setText(recyclerModel.getHittedBall());
        holder.intentionalBaseOnBalls.setText(recyclerModel.getIntentionalBaseOnBalls());
        holder.strikeOut.setText(recyclerModel.getStrikeOut());
        holder.doublePlay.setText(recyclerModel.getDoublePlay());
        holder.sacrificeRound.setText(recyclerModel.getSacrificeRound());
        holder.sacrificeFly.setText(recyclerModel.getSacrificeFly());
        holder.battingRate.setText(recyclerModel.getBattingRate());
        holder.baseOnRate.setText(recyclerModel.getBaseOnRate());
        holder.slugRate.setText(recyclerModel.getSlugRate());
        holder.ops.setText(recyclerModel.getOps());
        holder.woba.setText(recyclerModel.getWoba());
        holder.wrcplus.setText(recyclerModel.getWrcplus());
        holder.war.setText(recyclerModel.getWar());
        holder.wpa.setText(recyclerModel.getWpa());

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
        TextView colum;
        TextView year;
        TextView teamName;
        TextView age;
        TextView position;
        TextView game;
        TextView battingCount;
        TextView realBattingCount;
        TextView round;
        TextView oneBase;
        TextView twoBase;
        TextView threeBase;
        TextView homeRun;
        TextView totalBase;
        TextView runBattedIn;
        TextView steal;
        TextView stealFail;
        TextView fourBall;
        TextView hittedBall;
        TextView intentionalBaseOnBalls;
        TextView strikeOut;
        TextView doublePlay;
        TextView sacrificeRound;
        TextView sacrificeFly;
        TextView battingRate;
        TextView baseOnRate;
        TextView slugRate;
        TextView ops;
        TextView woba;
        TextView wrcplus;
        TextView war;
        TextView wpa;

        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            colum =itemView.findViewById(R.id.player_colum);
            year =itemView.findViewById(R.id.player_year);
            teamName =itemView.findViewById(R.id.player_teamName);
            age =itemView.findViewById(R.id.player_age);
            position =itemView.findViewById(R.id.player_position);
            game =itemView.findViewById(R.id.player_game);
            battingCount =itemView.findViewById(R.id.player_battingCount);
            realBattingCount =itemView.findViewById(R.id.player_realBattingCount);
            round =itemView.findViewById(R.id.player_round);
            oneBase =itemView.findViewById(R.id.player_oneBase);
            twoBase =itemView.findViewById(R.id.player_twoBase);
            threeBase =itemView.findViewById(R.id.player_threeBase);
            homeRun =itemView.findViewById(R.id.player_homeRun);
            totalBase =itemView.findViewById(R.id.player_totalBase);
            runBattedIn =itemView.findViewById(R.id.player_runBattedIn);
            steal =itemView.findViewById(R.id.player_steal);
            stealFail =itemView.findViewById(R.id.player_steal_fail);
            fourBall =itemView.findViewById(R.id.player_fourBall);
            hittedBall =itemView.findViewById(R.id.player_hittedBall);
            intentionalBaseOnBalls =itemView.findViewById(R.id.player_intentionalBaseOnBalls);
            strikeOut =itemView.findViewById(R.id.player_strikeOut);
            doublePlay =itemView.findViewById(R.id.player_doublePlay);
            sacrificeRound =itemView.findViewById(R.id.player_sacrificeRound);
            sacrificeFly =itemView.findViewById(R.id.player_sacrifice_fly);
            battingRate =itemView.findViewById(R.id.player_battingRate);
            baseOnRate =itemView.findViewById(R.id.player_baseOnRate);
            slugRate =itemView.findViewById(R.id.player_slugRate);
            ops =itemView.findViewById(R.id.player_ops);
            woba =itemView.findViewById(R.id.player_woba);
            wrcplus =itemView.findViewById(R.id.player_wrcPlus);
            war =itemView.findViewById(R.id.player_war);
            wpa=itemView.findViewById(R.id.player_wpa);
            listRoot = itemView.findViewById(R.id.id);
        }
    }


}