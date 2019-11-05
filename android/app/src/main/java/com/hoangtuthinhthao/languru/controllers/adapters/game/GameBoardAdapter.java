package com.hoangtuthinhthao.languru.controllers.adapters.game;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.hoangtuthinhthao.languru.R;

import java.util.ArrayList;

public class GameBoardAdapter extends BaseAdapter {
    private ArrayList<Button> mButtons = null;
    private int mColumnWidth, mColumnHeight;

    public GameBoardAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {return (Object) mButtons.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //here is where you inflate your layout containing your viewflipper
            view = inflater.inflate(R.layout.game_item, null);

        } else {
            holder = (ViewHolder) view.getTag();
        }

//        //reference the viewFlipper
//        ViewFlipper flipper = (viewFlipper) holder.findViewById(R.id.my_view_flipper);
//        //your front layout should be set to displayed be default
//
//
//        //now you can get get references to your textview or ImageViews contained within the layout
//        TextView name = (TextView) holder.findViewById(R.id.name);
//        name.setText("your text");

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
