package com.hoangtuthinhthao.languru.controllers.game;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.game.GameBoardAdapter;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

public class CreateGameBoard {
    private Context context;
    private GameGridView mGridView;
    private String[] tileList;

    private int mColumnWidth, mColumnHeight;
    private int DIMENSIONS;
    public CreateGameBoard(Context context, GameGridView gridView) {
        this.context = context;
        this.mGridView = gridView;
    }

    public void createGame ( ArrayList<Lesson> list, int level) {
        final Handler handler = new Handler();
        int row;
        int column;
        switch (level) {
            case 2:
                row = 4;
                column = 5;
                break;
            case 3:
                row = 5;
                column = 6;
                break;
            default:
                row = 3;
                column = 4;
                break;
        }

//        try {
//            LayoutInflater inflater = context.getLayoutInflater();
//            final TableLayout tbl = context.findViewById(R.id.tableGameBoard);
//            int width = tbl.getWidth();
//            int cellWidth = width/column;
//            TableRow tr;
//
//            tbl.removeAllViews();
//
//            TableLayout.LayoutParams lp =
//                    new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
//                            TableLayout.LayoutParams.WRAP_CONTENT);
//
//            lp.setMargins(5,5,5,5); // left, top, right, bottom
//            //float scale = context.getResources().getDisplayMetrics().density;
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            for (int x=0; x < row; x++) {
//                tr = new TableRow(context);
//                tr.setLayoutParams(lp);
//                // getting the columns
//                for (int y=0; y < column; y++) {
//                    RelativeLayout cell = (RelativeLayout) inflater.inflate(R.layout.game_item,null);
//                    //cell.getLayoutParams().width = 30;
//                    final ImageView img = cell.findViewById(R.id.gameImageView);
//                    img.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            GameCardAnimation.flip(img, R.drawable.ic_game);
//                            handler.postDelayed(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    GameCardAnimation.flip(img, R.drawable.ic_email);
//                                }
//
//                            }, 1500);
//                        }
//                    });
//
//                    try {
//                        tr.addView(cell);
//                    } catch (Exception e) {
//                        Log.d(this.getClass().getName(), e.getMessage());
//                    }
//
//                }
//                tbl.addView(tr);
//            }
//        } catch (Exception e) {
//            Log.d(this.getClass().getName(), e.getMessage());
//        }

        DIMENSIONS = column * row;
        mGridView.setNumColumns(column);
        tileList = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = String.valueOf(i);
        }
        //display(context);
        setDimensions(column, row);

    }

    /**
     * set the dimension of a game cell
     * @param numColumn number of column in game
     * @param numRow number of row in game
     */
    private void setDimensions(final int numColumn, final int numRow) {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(context.getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / numColumn;
                mColumnHeight = requiredHeight / numRow;

                display(context.getApplicationContext());
            }
        });
    }

    /**
     * This function get the height of status bar
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < DIMENSIONS; i++) {
            button = new Button(context);

            if (tileList[i].equals( i + ""))
                button.setBackgroundResource(R.drawable.ic_cell);


            buttons.add(button);
        }

        mGridView.setAdapter(new GameBoardAdapter(buttons, mColumnWidth, mColumnHeight));
    }
}
