// src/http-common.js
import axios from "axios";

const backendHost = import.meta.env.VITE_BACKEND_SERVER;
const backendPort = import.meta.env.VITE_BACKEND_PORT;

console.log(`Conectando a backend: http://${backendHost}:${backendPort}`);

export default axios.create({
  baseURL: `http://${backendHost}:${backendPort}`,
  headers: {
    "Content-Type": "application/json",
  },
});