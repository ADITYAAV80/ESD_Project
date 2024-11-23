import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PlacementRequestForm from "./components/PlacementRequestForm";
import LoginPage from "./components/LoginPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/placement" element={<PlacementRequestForm />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </Router>
  );
}

export default App;
