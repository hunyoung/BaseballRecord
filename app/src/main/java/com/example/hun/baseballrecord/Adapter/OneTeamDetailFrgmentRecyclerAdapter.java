package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.OneTeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.Model.TeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class OneTeamDetailFrgmentRecyclerAdapter extends RecyclerView.Adapter<OneTeamDetailFrgmentRecyclerAdapter.ViewHolder>{
    private  String TAG = "OneTeamDetailFrgmentRecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<OneTeamDetailFragmentRecyclerModel> dataList;
    public int curPosition = 0;

    public OneTeamDetailFrgmentRecyclerAdapter(Context context, int resourceId, List<OneTeamDetailFragmentRecyclerModel>dataList){
        this.context=context;
        this.resourceId=resourceId;
        this.dataList=dataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));

    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String playerName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        final OneTeamDetailFragmentRecyclerModel recyclerModel = dataList.get(position);
        holder.newsTitle.setText(recyclerModel.getName());
        holder.newsImage.setText(recyclerModel.getIcon());


        holder.getListRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curPosition = holder.getAdapterPosition();
                Log.d(TAG, "curPosition ==> " + curPosition);
                mItemClickListener.onItemClick(view, curPosition, recyclerModel.getName());
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
        TextView newsTitle;
        TextView newsImage;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsTitle = itemView.findViewById(R.id.news_title);

            listRoot = itemView.findViewById(R.id.id);
        }
    }


}