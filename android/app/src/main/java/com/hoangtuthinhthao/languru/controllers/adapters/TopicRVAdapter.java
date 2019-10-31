package com.hoangtuthinhthao.languru.controllers.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoangtuthinhthao.languru.R;

import java.util.ArrayList;


public class TopicRVAdapter extends RecyclerView.Adapter<TopicRVAdapter.ViewHolder>{

    private ItemClickListener mClickListener;
    private ArrayList<String> lessonList;

    public TopicRVAdapter(){

    }

    public TopicRVAdapter(ArrayList<String> data, ItemClickListener mClickListener){
        this.lessonList = data;
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
        viewHolder.topicName.setText(lessonList.get(position));

    }

    @Override
    public int getItemCount() {
        if(lessonList != null) {
            return lessonList.size();
        } return 0;
    }

    public void changeData( ArrayList<String> data){
        this.lessonList = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView topicName;
        ImageView topicImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            topicImage = itemView.findViewById(R.id.topicImage);

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
