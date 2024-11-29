import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import PlacementRequestForm from "./components/PlacementRequestForm";
import LoginPage from "./components/LoginPage";

// ProtectedRoute Component
const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem("token"); // Check for token (authentication)
  return token ? children : <Navigate to="/login" />; // Redirect to /login if not authenticated
};

function App() {
  return (
    <Router>
      <Routes>
        {/* Public Route */}
        <Route path="/login" element={<LoginPage />} />

        {/* Protected Route */}
        <Route
          path="/placement"
          element={
            <ProtectedRoute>
              <PlacementRequestForm />
            </ProtectedRoute>
          }
        />

        {/* Redirect root to login */}
        <Route path="/" element={<Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;
