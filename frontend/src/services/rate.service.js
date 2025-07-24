import httpRate from "../http-common";

const getAll = () => {
    return httpRate.get('/api/v1/rates/');
}

const create = data => {
    return httpRate.post("/api/v1/rates/register", data);
}

const get = id => {
    return httpRate.get(`/api/v1/rates/${id}`);
}

const update = (id, data) => {
    return httpRate.put(`/api/v1/rates/${id}`, data); 
}

const remove = id => {
    return httpRate.delete(`/api/v1/rates/${id}`);
}
export default { getAll, create, get, update, remove };