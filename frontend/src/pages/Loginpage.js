import React, { useState } from 'react';
import { useAuth } from '../auth/AuthContext';
import { useNavigate } from 'react-router-dom';
import './login.css';

const LoginPage = () => {
  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('password');
  const [error, setError] = useState('');
  const { mockLogin } = useAuth(); // Using mockLogin from context
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Use the mock login function from the authentication context
    const success = mockLogin(username, password);

    if (success) {
      navigate('/dashboard'); // Navigate on success
    } else {
      setError('Invalid credentials. Use admin/password to proceed.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2 className="text-center mb-4">Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="username"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <label htmlFor="username">Username</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <label htmlFor="password">Password</label>
          </div>
          
          {error && <div className="alert alert-danger p-2">{error}</div>}

          <p className="text-center text-muted small">
            (Use <strong>admin</strong> / <strong>password</strong> to continue)
          </p>

          <button type="submit" className="btn btn-primary w-100">Login</button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;