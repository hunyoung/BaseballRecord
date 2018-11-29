package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class MainMenuRecyclerAdapter extends RecyclerView.Adapter<MainMenuRecyclerAdapter.ViewHolder>{

    private Context context;
    private int resourceId;
    private List<MainMenuRecyclerModel> dataList;
    private int curPosition = 0;

    public MainMenuRecyclerAdapter(Context context, int resourceId, List<MainMenuRecyclerModel> dataList){
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
        MainMenuRecyclerModel recyclerModel = dataList.get(position);
        holder.one.setText((recyclerModel.getOne()));
//        holder.two.setText(recyclerModel.getTwo());
//        holder.three.setText(recyclerModel.getThree());


        holder.listRoot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                curPosition = holder.getAdapterPosition();
                Log.d("menu position", "curPosition ==> " + curPosition);
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
        TextView one;
//        TextView two;
//        TextView three;
        View listRoot;


        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);

            one =itemView.findViewById(R.id.one);
//            two =itemView.findViewById(R.id.two);
//            three =itemView.findViewById(R.id.three);
            listRoot = itemView.findViewById(R.id.menu_linear);

        }
    }


}