import httpReservation from "../http-common";

const getAll = () => {
    return httpReservation.get('/api/v1/reservations/');
}

const create = data => {
    return httpReservation.post("/api/v1/reservations/register", data);
}

const get = id => {
    return httpReservation.get(`/api/v1/reservations/${id}`);
}

const update = (id, data) => {
    return httpReservation.put(`/api/v1/reservations/${id}`, data); 
}

const remove = id => {
    return httpReservation.delete(`/api/v1/reservations/${id}`);
}
export default { getAll, create, get, update, remove };