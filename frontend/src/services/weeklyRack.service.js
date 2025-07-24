import httpWeeklyRack from "../http-common";

const getAll = () => {
    return httpWeeklyRack.get('/api/v1/weeklyRacks/');
}

const create = data => {
    return httpWeeklyRack.post("/api/v1/weeklyRacks/register", data);
}

const get = id => {
    return httpWeeklyRack.get(`/api/v1/weeklyRacks/${id}`);
}

const update = (id, data) => {
    return httpWeeklyRack.put(`/api/v1/weeklyRacks/${id}`, data); 
}

const remove = id => {
    return httpWeeklyRack.delete(`/api/v1/weeklyRacks/${id}`);
}
export default { getAll, create, get, update, remove };