package com.hoangtuthinhthao.languru.models.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.os.Handler;
import android.widget.Button;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;


/**
 * This class contains all information about a card cell
 */
public class GameCell {
    private Context context;
    private String label;
    private String url = "";
    private boolean isImage = false;
    private boolean isOpen = false;
    private boolean isCorrect = false;
    private Runnable flipBackRunnable;
    private Handler handler = new Handler();
    private Bitmap cellBitmap;

    private Button buttonView;

    public GameCell(Context context) {
        this.context = context;
    }

    // getters and setters

    public Bitmap getCellBitmap() {
        return cellBitmap;
    }

    public void setCellBitmap(Bitmap cellBitmap) {
        this.cellBitmap = cellBitmap;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean image) {
        isImage = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
        clearRunable();
    }

    public  Button getView() {
        buttonView = new Button(context);

        buttonView.setBackgroundResource(R.drawable.ic_cell);
//        if (isImage()) {
//            BitmapDrawable ob = new BitmapDrawable(context.getResources(), cellBitmap);
//            buttonView.setBackground(ob);
//        } else {
//            buttonView.setText(label);
//            buttonView.setBackgroundColor(Color.WHITE);
//        }
//        buttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GameCardAnimation.flip(buttonView, R.drawable.ic_game);
//                buttonView.setText("");
//            }
//        });
        return buttonView;
    }

    // this function set the text for button
    public void setWord() {
        if(buttonView != null) {
            buttonView.setBackgroundResource(R.drawable.bg_round_btn_white);
            buttonView.setText(label);
        }
    }

    //get open cells


    public void openCell(final Button btn) {
        if (isOpen) {
            clearRunable();
        } else {
            // Open the card
            if(!isImage) {
                GameCardAnimation.flipWord(btn, label);

            } else {
                GameCardAnimation.flipImage(context, btn, cellBitmap);
            }
            //set open property to true
            setOpen(true);
        }

        flipBackRunnable = new Runnable() {
            @Override
            public void run() {
                GameCardAnimation.flipBack(btn, isImage());
                setOpen(false);
            }
        };
        handler.postDelayed(flipBackRunnable , 1500);
    }

    public void clearRunable() {
        handler.removeCallbacks(flipBackRunnable);
    }
}
