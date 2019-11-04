const PREFIX = "LESSONS_";

export const LOAD_LESSONS = `${PREFIX}LOAD_LESSONS`;
export const LOAD_LESSONS_SUCCESS = `${PREFIX}LOAD_LESSONS_SUCCESS`;
export const LOAD_LESSONS_ERROR = `${PREFIX}LOAD_LESSONS_ERROR`;

export function loadLessons(topic) {
    return {
        type: LOAD_LESSONS,
        payload: topic,
    };
}
export function loadLessonsSuccess(payload) {
    return {
        type: LOAD_LESSONS_SUCCESS,
        payload,
    };
}
export function loadLessonsError(payload) {
    return {
        type: LOAD_LESSONS_ERROR,
        payload,
    };
}