import { all } from "redux-saga/effects";
import loginSagas from '../containers/Login/login.saga';
import registerSagas from '../containers/Register/register.saga';
import gameSagas from '../containers/Game/game.saga';
import headerSagas from '../containers/Header/header.saga';
import lessonsSagas from '../containers/Lessons/lessons.saga';

export default function* rootSaga(getState) {
    yield all([
        loginSagas(), 
        registerSagas(), 
        gameSagas(), 
        headerSagas(),
        lessonsSagas()
    ]);
}