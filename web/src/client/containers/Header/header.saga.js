import { put, takeLatest, all, call, takeEvery } from 'redux-saga/effects';
import * as HEADER_ACTION from "./header.action";
import {getProgress} from '../../libs/user.lib';

function* fetchTopics() {
    try{
        const res = yield call(getProgress);
        yield put(HEADER_ACTION.loadTopics(res.data));      
    }
    catch (e){
        console.log(e)
    }
}
export default function* rootSaga() {
    yield all([
        takeLatest(HEADER_ACTION.GET_TOPICS, fetchTopics)
    ]);
}