package com.hoangtuthinhthao.languru.controllers.adapters.game;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;
import com.hoangtuthinhthao.languru.models.game.Game;
import com.hoangtuthinhthao.languru.models.game.GameCell;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoardAdapter extends BaseAdapter {
   // private ArrayList<Button> mButtons = null;
    private Game game;
    private int mColumnWidth, mColumnHeight;
    private ItemClickListener mListener;
    private ArrayList<GameCell> cells;

    public GameBoardAdapter(Game game, int columnWidth, int columnHeight, ItemClickListener callback) {
       // mButtons = buttons;
        this.game = game;
        cells = game.getCardList();
        Collections.shuffle(cells);
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
        this.mListener = callback;
    }

    @Override
    public int getCount() {
        return game.getCardList().size();
    }

    @Override
    public Object getItem(int position) {return (Object) game.getCardList().get(position).getView();}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button = cells.get(position).getView();
//        if (convertView == null) {
//            button = mButtons.get(position);
//        } else {
//            button = (Button) convertView;
//        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });


        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);


        return button;
    }
}
