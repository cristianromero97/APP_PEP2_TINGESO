import httpDiscountVisit from "../http-common";

const getAll = () => {
    return httpDiscountVisit.get('/api/v1/discounts_visit/');
}

const create = data => {
    return httpDiscountVisit.post("/api/v1/discounts_visit/register", data);
}

const get = id => {
    return httpDiscountVisit.get(`/api/v1/discounts_visit/${id}`);
}

const update = (id, data) => {
    return httpDiscountVisit.put(`/api/v1/discounts_visit/${id}`, data); 
}

const remove = id => {
    return httpDiscountVisit.delete(`/api/v1/discounts_visit/${id}`);
}
export default { getAll, create, get, update, remove };