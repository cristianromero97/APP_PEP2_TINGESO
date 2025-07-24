import httpPayment from "../http-common";

const getAll = () => {
    return httpPayment.get('/api/v1/payments/');
}

const create = data => {
    return httpPayment.post("/api/v1/payments/register", data);
}

const get = id => {
    return httpPayment.get(`/api/v1/payments/${id}`);
}

const update = (id, data) => {
    return httpPayment.put(`/api/v1/payments/${id}`, data); 
}

const updateState = (id, newState) => {
    return httpPayment.put(`/api/v1/payments/${id}/state`, newState);
  };

const remove = id => {
    return httpPayment.delete(`/api/v1/payments/${id}`);
}

const sendPaymentPdf = (id) => {
    return httpPayment.post(`/api/v1/payments/${id}/send-pdf`);
}

export default { getAll, create, get, update, remove , updateState, sendPaymentPdf };