import httpDiscount from "../http-common";

const getAll = () => {
    return httpDiscount.get('/api/v1/discounts/');
}

const create = data => {
    return httpDiscount.post("/api/v1/discounts/register", data);
}

const get = id => {
    return httpDiscount.get(`/api/v1/discounts/${id}`);
}

const update = (id, data) => {
    return httpDiscount.put(`/api/v1/discounts/${id}`, data); 
}

const remove = id => {
    return httpDiscount.delete(`/api/v1/discounts/${id}`);
}
export default { getAll, create, get, update, remove };