import axios from "../config/initializers/axios";

const path = `/user`;

export const login = (payload) => {
        return axios.post(`${path}/login`, {...payload, clientType: 'browser'}).then(res => res);
};
export const logout = (payload) => {
    return axios.put(`${path}/logout`, payload).then(res => res);
};