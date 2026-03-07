import { useAuth } from '../context/AuthContext';
import Navbar from '../components/Navbar';

const quests = [
  { icon: '🚴', title: 'Bike to Work', desc: 'Skip the car for a day and cycle instead.', pts: 50 },
  { icon: '♻️', title: 'Recycle 5 Items', desc: 'Sort and recycle at least 5 household items.', pts: 30 },
  { icon: '💧', title: 'Save Water', desc: 'Take a shower under 5 minutes today.', pts: 20 },
  { icon: '🌱', title: 'Plant Something', desc: 'Plant a seed or care for a houseplant.', pts: 40 },
];

export default function Dashboard() {
  const { user } = useAuth();

  return (
    <div className="app-layout">
      <Navbar />
      <main className="main-content">
        <div className="dashboard-hero">
          <h1>Hello, <span>{user?.name?.split(' ')[0]}</span> 👋</h1>
          <p>You're making a difference. Keep up your eco habits!</p>
        </div>

        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">🌟</div>
            <div className="stat-value">{user?.points ?? 0}</div>
            <div className="stat-label">Eco Points</div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">✅</div>
            <div className="stat-value">0</div>
            <div className="stat-label">Quests Completed</div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">🔥</div>
            <div className="stat-value">1</div>
            <div className="stat-label">Day Streak</div>
          </div>
        </div>

        <div className="quest-section">
          <h2>Available Quests</h2>
          <div className="quest-cards">
            {quests.map((q, i) => (
              <div className="quest-card" key={i}>
                <div className="quest-icon">{q.icon}</div>
                <div>
                  <h4>{q.title}</h4>
                  <p>{q.desc}</p>
                  <span className="quest-points">⭐ {q.pts} pts</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
}
