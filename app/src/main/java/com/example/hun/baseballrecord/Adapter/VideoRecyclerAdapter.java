package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.YouTubeSearchModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder>{
    private  String TAG = "VideoRecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<YouTubeSearchModel> dataList;
    public int curPosition = 0;

    public VideoRecyclerAdapter(Context context, int resourceId, List<YouTubeSearchModel>dataList){
        this.context=context;
        this.resourceId=resourceId;
        this.dataList=dataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));

    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        Log.d(TAG, "onBindViewHolder");
        final YouTubeSearchModel recyclerModel = dataList.get(position);
//        holder.videoID.setText(recyclerModel.getVideoId());
        holder.changString.setText(recyclerModel.getTitle());
//        holder.imgUrl.setText(recyclerModel.getUrl());
        holder.date.setText(recyclerModel.getPublishedAt());


        holder.getListRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curPosition = holder.getAdapterPosition();
                Log.d(TAG, "curPosition ==> " + curPosition);
                mItemClickListener.onItemClick(view, curPosition);

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
 //       TextView videoID;
        TextView changString;
     //   ImageView imgUrl;
        TextView date;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
    //        videoID = itemView.findViewById(R.id.naver_news_title);
            changString = itemView.findViewById(R.id.title);
     //       imgUrl = itemView.findViewById(R.id.img);
            date = itemView.findViewById(R.id.date);


            listRoot = itemView.findViewById(R.id.id);
        }
    }


}
