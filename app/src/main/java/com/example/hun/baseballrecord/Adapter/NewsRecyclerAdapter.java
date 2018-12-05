package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.NewsModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>{
    private  String TAG = "OneTeamDetailFrgmentRecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<NewsModel> dataList;
    public int curPosition = 0;

    public NewsRecyclerAdapter(Context context, int resourceId, List<NewsModel>dataList){
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
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        final NewsModel recyclerModel = dataList.get(position);
        holder.newsTitle.setText(recyclerModel.getTitle());
        holder.newsDescription.setText(recyclerModel.getDescription());
        holder.newsLink.setText(recyclerModel.getLink());
        holder.newsPubDate.setText(recyclerModel.getPubdate());


        holder.getListRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curPosition = holder.getAdapterPosition();
                Log.d(TAG, "curPosition ==> " + curPosition);
                Log.d(TAG, "url link ===>    " + recyclerModel.getLink());
                Linkify.addLinks(holder.newsLink, Linkify.WEB_URLS);

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
        TextView newsTitle;
        TextView newsDescription;
        TextView newsLink;
        TextView newsPubDate;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);
            newsTitle = itemView.findViewById(R.id.naver_news_title);
            newsDescription = itemView.findViewById(R.id.naver_news_description);
            newsLink = itemView.findViewById(R.id.naver_news_link);
            newsPubDate = itemView.findViewById(R.id.naver_news_pubdate);


            listRoot = itemView.findViewById(R.id.id);
        }
    }


}
