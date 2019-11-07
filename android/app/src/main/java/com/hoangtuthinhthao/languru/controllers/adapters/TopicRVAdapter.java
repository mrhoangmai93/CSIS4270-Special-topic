package com.hoangtuthinhthao.languru.controllers.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.models.responses.Progress;

import java.math.BigDecimal;
import java.util.ArrayList;


public class TopicRVAdapter extends RecyclerView.Adapter<TopicRVAdapter.ViewHolder>{

    private ItemClickListener mClickListener;
    private ArrayList<Progress> topicList;

    public TopicRVAdapter(){

    }

    public TopicRVAdapter(ArrayList<Progress> data, ItemClickListener mClickListener){
        this.topicList = data;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater linf = LayoutInflater.from(viewGroup.getContext());
        View itemView = linf.inflate(R.layout.rv_item_topic, viewGroup, false);
        ViewHolder itemViewHolder = new ViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.topicName.setText(topicList.get(position).getTopic());
        BigDecimal bigDecimal = new BigDecimal(topicList.get(position).getProgress());
        int intValue = bigDecimal.intValue();
        viewHolder.circleProgress.setProgress(intValue);

    }

    @Override
    public int getItemCount() {
        if(topicList != null) {
            return topicList.size();
        } return 0;
    }

    public void changeData( ArrayList<Progress> data){
        this.topicList = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView topicName;
        CircleProgress circleProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            //topicImage = itemView.findViewById(R.id.topicImage);
            circleProgress = itemView.findViewById(R.id.topicProgress);
            int myColor = Color.parseColor("#2DCE00");
            circleProgress.setFinishedColor(myColor);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
