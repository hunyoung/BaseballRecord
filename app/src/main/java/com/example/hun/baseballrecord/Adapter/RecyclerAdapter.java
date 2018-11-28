package com.example.hun.baseballrecord.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private Context context;
    private int resourceId;
    private List<RecyclerModel> dataList;
    private int curPosition = 0;

    public RecyclerAdapter(Context context, int resourceId, List<RecyclerModel>dataList){
        this.context=context;
        this.resourceId=resourceId;
        this.dataList=dataList;

    }

    View.OnFocusChangeListener l;
    public void set(View.OnFocusChangeListener l) {
        this.l = l;
    }

//    View.OnClickListener clickListener;
//    public void OnClickListener(View.OnClickListener onClickListener) {
//        this.clickListener = onClickListener;
//    }
//
//
//    View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (mItemClickListener != null) {
//                //int i = v.getAccessibilityViewId();
//                int i = v.getVerticalScrollbarPosition();
////                int i = v.getVerticalScrollbarPosition();
//                mItemClickListener.onItemClick(v, i);
//                Log.d("111", "i ==> " + i);
//                Log.d("111", "v getAccessibilityLiveRegion==> " + v.getAccessibilityLiveRegion());
//                Log.d("111", "v getBottom==> " + v.getBottom());
//                Log.d("111", "v getId==> " + v.getId());
//
//            }
//        }
//    };
//
//    private OnItemClickListener mItemClickListener;
//
//    public interface OnItemClickListener {
//        void onItemClick(View v, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mItemClickListener = listener;
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(resourceId,parent,false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        RecyclerModel recyclerModel = dataList.get(position);
        holder.rankText.setText((recyclerModel.getRank()));
        holder.nameText.setText(recyclerModel.getName());
        holder.warText.setText(recyclerModel.getWar());

        position = holder.getAdapterPosition();

        //holder.getListRoot().setOnClickListener(mOnClickListener);

        holder.listRoot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                curPosition = holder.getAdapterPosition();
                Log.d("Aaaaaaaaaaa", "curPosition ==> " + curPosition);
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
        TextView warText;
        View listRoot;


        public View getListRoot() {
            return listRoot;
        }

        public ViewHolder(View itemView){
            super(itemView);

            rankText =itemView.findViewById(R.id.rankText);
            nameText =itemView.findViewById(R.id.nameText);
            warText =itemView.findViewById(R.id.warText);
            listRoot = itemView.findViewById(R.id.id);

            itemView.setOnFocusChangeListener(l);
          //  itemView.setOnClickListener(clickListener);
        }
    }


}