import { useState, useEffect } from 'react';
import { userApi } from '../services/api';
import { useAuth } from '../context/AuthContext';
import Navbar from '../components/Navbar';

export default function Profile() {
  const { user, updateUser } = useAuth();
  const [name, setName] = useState(user?.name || '');
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    // Fetch fresh profile data from backend
    userApi.getProfile()
      .then((res) => {
        setProfile(res.data);
        setName(res.data.name);
      })
      .catch(() => {
        // Fallback to context data
        setProfile(user);
      });
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const res = await userApi.updateProfile({ name });
      setProfile(res.data);
      updateUser(res.data);
      setSuccess('Profile updated successfully!');
    } catch (err) {
      setError(err.response?.data?.error || 'Update failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const displayName = profile?.name || user?.name || '';
  const initials = displayName.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);

  return (
    <div className="app-layout">
      <Navbar />
      <main className="main-content">
        <div className="profile-container">
          <div className="profile-header">
            <div className="profile-avatar">{initials || '?'}</div>
            <div className="profile-info">
              <h2>{displayName}</h2>
              <p className="profile-email">{profile?.email || user?.email}</p>
              <div className="profile-points-badge">
                🌟 {profile?.points ?? user?.points ?? 0} Eco Points
              </div>
            </div>
          </div>

          <div className="profile-edit-card">
            <h3>Edit Profile</h3>

            {success && <div className="success-message">{success}</div>}
            {error && <div className="error-message">{error}</div>}

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="name">Full Name</label>
                <input
                  id="name"
                  type="text"
                  value={name}
                  onChange={(e) => { setName(e.target.value); setSuccess(''); setError(''); }}
                  required
                  placeholder="Your full name"
                />
              </div>

              <div className="form-group">
                <label>Email Address</label>
                <input
                  type="email"
                  value={profile?.email || user?.email || ''}
                  disabled
                  style={{ opacity: 0.6, cursor: 'not-allowed', background: '#f5f5f5' }}
                />
              </div>

              <button type="submit" className="btn btn-primary btn-sm" disabled={loading}>
                {loading && <span className="spinner" />}
                {loading ? 'Saving...' : 'Save Changes'}
              </button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
}
