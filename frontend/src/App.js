import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import FairDelivery from "./pages/FairDelivery";
import ShopSupply from "./pages/ShopSupply";
import Loginpage from "./pages/Loginpage";

function App() {
  return (
    <Router>
      <Routes>
        {/* Login first */}
        <Route path="/" element={<Loginpage />} />

        {/* Dashboard */}
        <Route path="/dashboard" element={<Dashboard />} />

        {/* Buttons navigation */}
        <Route path="/fair-delivery" element={<FairDelivery />} />
        <Route path="/shop-supply" element={<ShopSupply />} />

        {/* Reports (placeholder for now) */}
        <Route path="/reports" element={<h2>Reports Page Coming Soon</h2>} />
      </Routes>
    </Router>
  );
}

export default App;
