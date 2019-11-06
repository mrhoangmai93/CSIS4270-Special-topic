package com.hoangtuthinhthao.languru.controllers.game;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;
import com.hoangtuthinhthao.languru.models.game.Game;
import com.hoangtuthinhthao.languru.models.game.GameCell;

public class GameCalculation {
    private Context context;
    private Game game;
    private GameCompleteCallback callback;
    public GameCalculation(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    public void setCellClick(View view, int position) {
        final GameCell cell = game.getCardList().get(position);
        Button btn = (Button) view;
        switch (game.getNumCardOpen()) {
            case 0:
            case 1:
                case 2:
                if (!cell.isCorrect()){
                    cell.openCell(btn);
                }

                break;
                default:
                    break;
        }
        if(!cell.isCorrect()) {
            game.checkMatch();
            if(game.isGameComplete()) {
                callback.onGameComplete();
            }
        }

    }

    public void setCallback(GameCompleteCallback callback) {
        this.callback = callback;
    }

    public interface GameCompleteCallback {
        void onGameComplete();
    }
}
