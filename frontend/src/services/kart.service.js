import httpKart from "../http-common";

const getAll = () => {
    return httpKart.get('/api/v1/karts/');
}

const create = data => {
    return httpKart.post("/api/v1/karts/register", data);
}

const get = id => {
    return httpKart.get(`/api/v1/karts/${id}`);
}

const update = (id, data) => {
    return httpKart.put(`/api/v1/karts/${id}`, data); 
}

const remove = id => {
    return httpKart.delete(`/api/v1/karts/${id}`);
}
export default { getAll, create, get, update, remove };