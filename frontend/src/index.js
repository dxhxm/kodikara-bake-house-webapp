import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/Loginpage";
import Dashboard from "./pages/Dashboard";
import FairDelivery from "./pages/FairDelivery";
import ShopSupply from "./pages/ShopSupply";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/fair-delivery" element={<FairDelivery />} />
        <Route path="/shop-supply" element={<ShopSupply />} />
      </Routes>
    </Router>
  );
}

export default App;
