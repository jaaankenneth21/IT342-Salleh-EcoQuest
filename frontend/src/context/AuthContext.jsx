import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const name = localStorage.getItem('name');
    const email = localStorage.getItem('email');
    const points = localStorage.getItem('points');

    if (token && name && email) {
      setUser({ name, email, points: parseInt(points) || 0 });
    }
    setLoading(false);
  }, []);

  const login = (data) => {
    localStorage.setItem('token', data.token);
    localStorage.setItem('name', data.name);
    localStorage.setItem('email', data.email);
    localStorage.setItem('points', data.points ?? 0);
    setUser({ name: data.name, email: data.email, points: data.points ?? 0 });
  };

  const updateUser = (data) => {
    localStorage.setItem('name', data.name);
    localStorage.setItem('points', data.points ?? 0);
    setUser((prev) => ({ ...prev, name: data.name, points: data.points ?? 0 }));
  };

  const updatePoints = (newPoints) => {
    localStorage.setItem('points', newPoints);
    setUser((prev) => ({ ...prev, points: newPoints }));
  };

  const logout = () => {
    localStorage.clear();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{
      user,
      setUser,
      loading,
      login,
      logout,
      updateUser,
      updatePoints
    }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}