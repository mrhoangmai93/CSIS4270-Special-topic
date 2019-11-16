package com.hoangtuthinhthao.languru.views.fragments.games;


public interface OnGameCenterInteraction {
    void onGameItemClick(int id);
    void onBackButtonPressed();
    void onChalengeGameInteraction(String type, String code);
}
