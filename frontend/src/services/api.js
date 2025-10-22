import axios from "axios";

// Create Axios instance with base URL
const api = axios.create({
  baseURL: "http://localhost:8080/api", // Spring Boot backend base URL
});

// You can also add interceptors if needed later (auth, errors, etc.)

export default api; 