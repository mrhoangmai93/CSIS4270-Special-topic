const PREFIX = "HEADER_";

export const GET_TOPICS = `${PREFIX}GET_TOPICS`;
export const LOAD_TOPICS = `${PREFIX}LOAD_TOPICS`;

export const getTopics = () => {
    return {
        type: GET_TOPICS,
    }
}

export function loadTopics(payload) {
    return {
        type: LOAD_TOPICS,
        payload,
    };
}
