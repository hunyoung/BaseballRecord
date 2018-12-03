package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.TeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.Model.TeamFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class TeamDetailFrgmentRecyclerAdapter extends RecyclerView.Adapter<TeamDetailFrgmentRecyclerAdapter.ViewHolder>{
    private  String TAG = "TeamDetailFrgmentRecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<TeamDetailFragmentRecyclerModel> dataList;
    public int curPosition = 0;

    public TeamDetailFrgmentRecyclerAdapter(Context context, int resourceId, List<TeamDetailFragmentRecyclerModel>dataList){
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
        TeamDetailFragmentRecyclerModel recyclerModel = dataList.get(position);
        holder.nameText.setText(recyclerModel.getName());
//        holder.teamIcon.setImageDrawable(recyclerModel.getIcon());
//        holder.teamIcon.setImageDrawable(recyclerModel.getIcon());
//        holder.teamIcon.image
        holder.teamIcon.setImageDrawable(recyclerModel.getIcon());

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
        TextView nameText;
        ImageView teamIcon;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            teamIcon = itemView.findViewById(R.id.team_icon);
            nameText = itemView.findViewById(R.id.team_name);

            listRoot = itemView.findViewById(R.id.id);
        }
    }


}