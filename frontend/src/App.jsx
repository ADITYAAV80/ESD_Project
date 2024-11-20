import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PlacementRequestForm from "./PlacementRequestForm";
import LoginPage from "./LoginPage";

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
