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
            <div className="kbh-cards">
                <Link to="/products" className="kbh-card kbh-brown">
                    <div className="kbh-card-title">Product Management</div>
                    <p className="kbh-card-text">Create, Edit, Delete, and Manage prices.</p>
                </Link>

                <Link to="/stock" className="kbh-card kbh-red">
                    <div className="kbh-card-title">Stock Management</div>
                    <p className="kbh-card-text">Record Stock counts and do Calculations</p>
                </Link>

                <Link to="/shops" className="kbh-card kbh-brown">
                    <div className="kbh-card-title">Shop Registration</div>
                    <p className="kbh-card-text">Register new shops and manage existing ones.</p>
                </Link>
            </div>

        </div>
      </div>
    </div>
  );
};

export default Dashboard;