package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private  String TAG = "RecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<RecyclerModel> dataList;
    public int curPosition = 0;

    public RecyclerAdapter(Context context, int resourceId, List<RecyclerModel>dataList){
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
        RecyclerModel recyclerModel = dataList.get(position);
        holder.rankText.setText((recyclerModel.getRank()));
        holder.nameText.setText(recyclerModel.getName());
        holder.teamText.setText(recyclerModel.getTeamName());

        if(recyclerModel.getTeamName().length() > 2){
            String tempTeam = recyclerModel.getTeamName().substring(2, 3);
            if(tempTeam.equals("두")){
                holder.teamText.setBackgroundColor(Color.parseColor("#000054"));
            } else if(tempTeam.equals("넥")){
                holder.teamText.setBackgroundColor(Color.parseColor("#740000"));
            } else if(tempTeam.equals("롯")){
                holder.teamText.setBackgroundColor(Color.parseColor("#000000"));
            }  else if(tempTeam.equals("K")){
                holder.teamText.setBackgroundColor(Color.parseColor("#c90000"));
            }  else if(tempTeam.equals("삼")){
                holder.teamText.setBackgroundColor(Color.parseColor("#001ec9"));
            }  else if(tempTeam.equals("L")){
                holder.teamText.setBackgroundColor(Color.parseColor("#000000"));
            }  else if(tempTeam.equals("S")){
                holder.teamText.setBackgroundColor(Color.parseColor("#ff1212"));
            }  else if(tempTeam.equals("N")){
                holder.teamText.setBackgroundColor(Color.parseColor("#002266"));
            }  else if(tempTeam.equals("한")){
                holder.teamText.setBackgroundColor(Color.parseColor("#ed4c00"));
            } else {
                holder.teamText.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            holder.teamText.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.teamText.setTextColor(Color.parseColor("#000000"));
        }



        holder.warText.setText(recyclerModel.getWar());
        holder.battingAvgText.setText(recyclerModel.getBattingAvg());
        holder.onBasePercentText.setText(recyclerModel.getOnBasePercent());
        holder.slugAvgText.setText(recyclerModel.getSlugAvg());
        holder.opsText.setText(recyclerModel.getOps());

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
        TextView teamText;
        TextView warText;
        TextView battingAvgText;
        TextView onBasePercentText;
        TextView slugAvgText;
        TextView opsText;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            rankText =itemView.findViewById(R.id.rankText);
            nameText =itemView.findViewById(R.id.nameText);
            teamText =itemView.findViewById(R.id.teamText);
            warText =itemView.findViewById(R.id.warText);
            battingAvgText =itemView.findViewById(R.id.battingAvgText);
            onBasePercentText =itemView.findViewById(R.id.onBasePercentText);
            slugAvgText =itemView.findViewById(R.id.slugAvgText);
            opsText =itemView.findViewById(R.id.opsText);
            listRoot = itemView.findViewById(R.id.id);
        }
    }


}