import httpDiscountDay from "../http-common";

const getAll = () => {
    return httpDiscountDay.get('/api/v1/discounts_day/');
}

const create = data => {
    return httpDiscountDay.post("/api/v1/discounts_day/register", data);
}

const get = id => {
    return httpDiscountDay.get(`/api/v1/discounts_day/${id}`);
}

const update = (id, data) => {
    return httpDiscountDay.put(`/api/v1/discounts_day/${id}`, data); 
}

const remove = id => {
    return httpDiscountDay.delete(`/api/v1/discounts_day/${id}`);
}
export default { getAll, create, get, update, remove };