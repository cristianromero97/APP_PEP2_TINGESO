import httpReport from "../http-common";

const getAll = () => {
    return httpReport.get('/api/v1/reports/');
}

const create = data => {
    return httpReport.post("/api/v1/reports/register", data);
}

const get = id => {
    return httpReport.get(`/api/v1/reports/${id}`);
}

const update = (id, data) => {
    return httpReport.put(`/api/v1/reports/${id}`, data); 
}

const remove = id => {
    return httpReport.delete(`/api/v1/reports/${id}`);
}

const generate = (type) => {
    return httpReport.post(`/api/v1/reports/register`, { type });
};

export default { getAll, create, get, update, remove ,generate  };