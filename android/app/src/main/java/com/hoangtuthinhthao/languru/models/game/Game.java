package com.hoangtuthinhthao.languru.models.game;

import android.content.Context;

import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

public class Game {

    private Context context;
    private int row;
    private int column;
    private int dimention;
    private int correctAnswer;
    private boolean gameComplete = false;
    private ArrayList<GameCell> cardList;


    public Game(Context context, int row, int column, ArrayList<Lesson> wordList) {
        this.context = context;
        this.row = row;
        this.column = column;
        this.dimention = row * column;
        this.correctAnswer = 0;


        cardList = new ArrayList<>();
        for ( Lesson word : wordList) {
            //create cell with word
            GameCell cardWord = new GameCell(context);
            cardWord.setLabel(word.getWord());
            // create cell with image
            GameCell cardImage = new GameCell(context);
            cardImage.setLabel(word.getWord());
            cardImage.setUrl(word.getImage());
            cardImage.setIsImage(true);
            cardImage.setCellBitmap(word.getImageBitmap());
            //add cell to array list
            cardList.add(cardWord);
            cardList.add(cardImage);
        }
    }


    // getters and setters

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getDimention() {
        return dimention;
    }

    public void setDimention(int dimention) {
        this.dimention = dimention;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<GameCell> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<GameCell> cardList) {
        this.cardList = cardList;
    }

    //Game control
    public int getNumCardOpen() {
        int count = 0;
        for(GameCell cell : cardList) {
            if(cell.isOpen() && !cell.isCorrect()) {
                count++;
            }
        }
        return count;
    }
    // check 2 cells open are match or not
    public void checkMatch() {
        GameCell cell1 = null;
        GameCell cell2 = null;
        for(GameCell cell : cardList) {
            if(cell.isOpen() && !cell.isCorrect()) {

                if(cell1 == null) {
                    cell1 = cell;
                } else {
                    cell2 = cell;
                }
            }
        }
        if(cell1 != null && cell2 != null) {
            //check match
            if(cell1.getLabel().equals(cell2.getLabel())) {
                cell1.setCorrect(true);
                cell2.setCorrect(true);
                correctAnswer++;
                if(correctAnswer == dimention /2)
                    setGameComplete(true);
            }
        }

    }
}
