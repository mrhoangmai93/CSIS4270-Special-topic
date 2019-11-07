package com.hoangtuthinhthao.languru.views.fragments.games;


public interface OnGameCenterInteraction {
    void onGameItemClick(int id);
    void onGameComplete(int numberOfWord);
    void onBackButtonPressed();
}
