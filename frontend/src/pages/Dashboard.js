import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import './dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1>Dashboard</h1>
        <button className="btn btn-outline-danger" onClick={handleLogout}>Logout</button>
      </div>
      
      <div className="card mb-3">
        <div className="card-body">
          <h5 className="card-title">Welcome, {user?.username || 'User'}!</h5>
          <p className="card-text">
            From here you can manage the different parts of your application.
          </p>
          <Link to="/products" className="btn btn-primary me-2">
            Go to Product Management
          </Link>
          <Link to="/stock" className="btn btn-info">
            Go to Stock Management
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;