import { all } from "redux-saga/effects";
import loginSagas from '../containers/Login/login.saga';
export default function* rootSaga(getState) {
    yield all([loginSagas()]);
}