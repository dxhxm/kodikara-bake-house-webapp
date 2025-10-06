import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './auth/AuthContext';
import ProtectedRoute from './auth/ProtectedRoute';
import LoginPage from './pages/Loginpage';
import Dashboard from './pages/Dashboard';
import Products from './pages/Products';
import StockManagement from './pages/StockManagement'; // New import
import './App.css';

// A component to handle the root path
const Root = () => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Navigate to="/dashboard" /> : <Navigate to="/login" />;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Root />} />
          <Route path="/login" element={<LoginPage />} />
          <Route 
            path="/dashboard" 
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />
          <Route 
            path="/products" 
            element={
              <ProtectedRoute>
                <Products />
              </ProtectedRoute>
            }
          />
          <Route 
            path="/stock" 
            element={
              <ProtectedRoute>
                <StockManagement />
              </ProtectedRoute>
            }
          />
          {/* Add other routes here */}
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
