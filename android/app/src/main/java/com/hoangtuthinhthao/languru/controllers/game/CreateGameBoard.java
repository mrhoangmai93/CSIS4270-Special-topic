package com.hoangtuthinhthao.languru.controllers.game;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.adapters.game.GameBoardAdapter;
import com.hoangtuthinhthao.languru.models.game.GameCell;
import com.hoangtuthinhthao.languru.models.game.Game;

import java.util.ArrayList;
import java.util.Collections;

public class CreateGameBoard {
    private Context context;
    private GameGridView mGridView;
    private ItemClickListener callback;
    private GameBoardAdapter gameBoardAdapter;
    private int mColumnWidth, mColumnHeight;
    private int DIMENSIONS;

    public CreateGameBoard(Context context, GameGridView gridView, ItemClickListener itemClickListener) {
        this.context = context;
        this.mGridView = gridView;
        this.callback = itemClickListener;
    }

    public void createGame (Game game, int row, int column) {

        DIMENSIONS = column * row;
        mGridView.setNumColumns(column);
        setDimensions(column, row, game);

    }

    /**
     * set the dimension of a game cell
     * @param numColumn number of column in game
     * @param numRow number of row in game
     */
    private void setDimensions(final int numColumn, final int numRow, final Game game) {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                mColumnWidth = displayWidth / numColumn;
                mColumnHeight = displayHeight / numRow;

                display(game);
            }
        });
    }

    /**
     * This function display the game board
     * @param game game data
     */
    private void display(Game game) {
        //ArrayList<GameCell> gameCells = game.getCardList();

//        ArrayList<Button> buttons = new ArrayList<>();
//
//        for(GameCell cell : gameCells) {
//            buttons.add(cell.getView());
//        }

        gameBoardAdapter = new GameBoardAdapter(game, mColumnWidth, mColumnHeight, callback);
        mGridView.setAdapter(gameBoardAdapter);

    }
}
