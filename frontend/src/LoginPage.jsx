import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"; // Import useNavigate for redirection
import "./LoginPage.css";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [responseMessage, setResponseMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate(); // Hook for redirection

  const handleSubmit = (e) => {
    e.preventDefault();

    // Basic validation
    if (!username || !password) {
      setResponseMessage("Please fill in both fields");
      return;
    }

    setIsLoading(true);
    setResponseMessage("");

    let send_info = {
      username: username,
      password: password,
    };

    console.log(send_info);

    // Send login request to backend
    axios
      .post("http://localhost:8080/login", send_info, {
        headers: {
          "Content-Type": "application/json", // Indicate that the data is JSON
        },
      })
      .then((response) => {
        setIsLoading(false);
        // Assuming the response contains a JWT token
        localStorage.setItem("token", response.data); // Store token in localStorage
        setResponseMessage("Login successful!");
        console.log("response", response.data);
        navigate("/placement"); // Redirect to placement page
      })
      .catch((error) => {
        setIsLoading(false);
        console.error("Login error:", error);
        setResponseMessage("Login failed. Please try again.");
      });
  };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit} className="login-form">
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <br />

        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <br />

        <button type="submit" disabled={isLoading}>
          {isLoading ? "Logging in..." : "Login"}
        </button>
      </form>

      {responseMessage && <p className="response-message">{responseMessage}</p>}
    </div>
  );
}
