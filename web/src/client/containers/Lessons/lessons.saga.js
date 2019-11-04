import { put, takeLatest, all, call } from 'redux-saga/effects';
import * as LESSONS_ACTION from "./lessons.action";
import {listByTopic} from '../../libs/lesson.lib';

function* fetchLessons({payload}) {
    try{
        const res = yield call(listByTopic, payload);
        yield put(LESSONS_ACTION.loadLessonsSuccess(res.data));      
    }
    catch (e){
        yield put(LESSONS_ACTION.loadLessonsError((e.response) ? e.response.data.message : 'Load lessons error'));
    }
}
export default function* rootSaga() {
    yield all([
        takeLatest(LESSONS_ACTION.LOAD_LESSONS, fetchLessons)
    ]);
}