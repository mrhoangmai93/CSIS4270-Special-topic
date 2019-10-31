package com.hoangtuthinhthao.languru.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

public class SingleImageRVAdapter extends RecyclerView.Adapter<SingleImageRVAdapter.ViewHolder>{

    private ItemClickListener mClickListener;
    private ArrayList<Lesson> lessonList;
    private Context context;
    public SingleImageRVAdapter(){

    }

    public SingleImageRVAdapter(Context context, ArrayList<Lesson> data, ItemClickListener mClickListener){
        this.context = context;
        this.lessonList = data;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater linf = LayoutInflater.from(viewGroup.getContext());
        View itemView = linf.inflate(R.layout.rv_item_image, viewGroup, false);
        ViewHolder itemViewHolder = new ViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Glide.with(context.getApplicationContext()).load(lessonList.get(position).getImage()).into(viewHolder.wordImage);
    }

    @Override
    public int getItemCount() {
        if(lessonList != null) {
            return lessonList.size();
        } return 0;
    }

    public void changeData( ArrayList<Lesson> data){
        this.lessonList = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView wordImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordImage = itemView.findViewById(R.id.rvImageView);

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
