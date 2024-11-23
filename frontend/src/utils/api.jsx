// utils/api.js
import axios from "axios";

// Function to handle login request
export const loginUser = (loginData) => {
  return axios
    .post("http://localhost:8080/login", loginData, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => response.data); // Return the response data (JWT token or response)
};

// Get specializations from the server
export const getSpecializations = (token) => {
  return axios
    .get("http://localhost:8080/api/specialization", {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      return response.data.map((spec) => ({
        value: spec.specialization_id,
        label: `${spec.code} - ${spec.name}`,
      }));
    });
};

// Get domains from the server
export const getDomains = (token) => {
  return axios
    .get("http://localhost:8080/api/domain", {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      return response.data.map((domain) => ({
        value: domain.domain_id,
        label: `${domain.program} - ${domain.batch}`,
      }));
    });
};

// Submit the placement request to the server
export const submitPlacementRequest = (placementRequest, token) => {
  return axios.post("http://localhost:8080/submit", placementRequest, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
