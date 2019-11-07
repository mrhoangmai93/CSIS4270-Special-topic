package com.hoangtuthinhthao.languru.models.game;

import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

public interface LoadGameCallback {
    void successLoadGame(int numberOfWord, ArrayList<Lesson> lessons);
    void failLoadGame(String message);
}
