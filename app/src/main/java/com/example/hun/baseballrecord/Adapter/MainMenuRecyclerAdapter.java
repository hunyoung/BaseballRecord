package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class MainMenuRecyclerAdapter extends RecyclerView.Adapter<MainMenuRecyclerAdapter.MainMenuViewHolder> {
    private static String TAG = "MainMenuRecyclerAdapter";
    private Context context;
    private int resourceId;
    private List<MainMenuRecyclerModel> dataList;
    private int curPosition = 0;
    private MainMenuViewHolder mHolder;

    public MainMenuRecyclerAdapter(Context context, int resourceId, List<MainMenuRecyclerModel> dataList) {
        this.context = context;
        this.resourceId = resourceId;
        this.dataList = dataList;
    }

    @Override
    public MainMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainMenuViewHolder(LayoutInflater.from(context).inflate(resourceId, parent, false));
    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final MainMenuViewHolder holder, int position) {
        MainMenuRecyclerModel recyclerModel = dataList.get(position);
        holder.one.setText((recyclerModel.getOne()));

        mHolder = holder;

        mHolder.getListRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curPosition = holder.getAdapterPosition();
                Log.d(TAG, "curPosition ==> " + curPosition);
                mItemClickListener.onItemClick(view, curPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class MainMenuViewHolder extends RecyclerView.ViewHolder {
        TextView one;
        View listRoot;

        public View getListRoot() {
            return listRoot;
        }

        public MainMenuViewHolder(View itemView) {
            super(itemView);
            one = itemView.findViewById(R.id.one);
            listRoot = itemView.findViewById(R.id.menu_linear);
        }
    }

}