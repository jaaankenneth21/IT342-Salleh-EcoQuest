import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="navbar">
      <NavLink to="/dashboard" className="logo">
        Eco<span>Quest</span>
      </NavLink>
      <nav>
        <NavLink to="/dashboard">Dashboard</NavLink>
        <NavLink to="/profile">Profile</NavLink>
        <button onClick={handleLogout} className="logout-btn">
          Log out
        </button>
      </nav>
    </header>
  );
}
