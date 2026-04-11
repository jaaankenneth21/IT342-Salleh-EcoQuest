import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import Navbar from '../components/Navbar';
import { questApi } from '../services/api';

export default function Quests() {
  const { user, updatePoints } = useAuth();
  const [quests, setQuests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [completing, setCompleting] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    fetchQuests();
  }, []);

  const fetchQuests = async () => {
    try {
      const response = await questApi.getAllQuests();
      setQuests(response.data);
    } catch (err) {
      setError('Failed to load quests. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleComplete = async (questId) => {
    setCompleting(questId);
    setSuccessMessage('');
    setError('');
    try {
      const response = await questApi.completeQuest(questId);
      const data = response.data;

      setQuests((prev) =>
        prev.map((q) =>
          q.id === questId ? { ...q, completed: true } : q
        )
      );

      updatePoints(data.totalPoints);

      setSuccessMessage(
        `✅ "${data.questTitle}" completed! You earned ${data.pointsEarned} pts. Total: ${data.totalPoints} pts`
      );

      setTimeout(() => setSuccessMessage(''), 4000);
    } catch (err) {
      if (err.response?.status === 409) {
        setError('You have already completed this quest.');
      } else {
        setError('Failed to complete quest. Please try again.');
      }
      setTimeout(() => setError(''), 3000);
    } finally {
      setCompleting(null);
    }
  };

  const getDifficultyColor = (difficulty) => {
    switch (difficulty) {
      case 'Easy': return '#2D9E6B';
      case 'Medium': return '#F59E0B';
      case 'Hard': return '#E05252';
      default: return '#2D9E6B';
    }
  };

  const completedCount = quests.filter((q) => q.completed).length;

  return (
    <div className="app-layout">
      <Navbar />
      <main className="main-content">

        <div className="dashboard-hero">
          <h1>🌿 Eco Quests</h1>
          <p>Complete quests to earn eco points and help the planet!</p>
        </div>

        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">🌟</div>
            <div className="stat-value">{user?.points ?? 0}</div>
            <div className="stat-label">Eco Points</div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">✅</div>
            <div className="stat-value">{completedCount}</div>
            <div className="stat-label">Completed</div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">📋</div>
            <div className="stat-value">{quests.length - completedCount}</div>
            <div className="stat-label">Remaining</div>
          </div>
        </div>

        {successMessage && (
          <div style={{
            background: '#D4F5E4',
            color: '#0D3B2E',
            padding: '12px 20px',
            borderRadius: '10px',
            marginBottom: '16px',
            fontWeight: '600',
            fontSize: '14px'
          }}>
            {successMessage}
          </div>
        )}

        {error && (
          <div style={{
            background: '#FFE5E5',
            color: '#E05252',
            padding: '12px 20px',
            borderRadius: '10px',
            marginBottom: '16px',
            fontWeight: '600',
            fontSize: '14px'
          }}>
            {error}
          </div>
        )}

        <div className="quest-section">
          <h2>Available Quests</h2>

          {loading ? (
            <p style={{ color: '#7A9E8A', padding: '20px 0' }}>
              Loading quests...
            </p>
          ) : (
            <div className="quest-cards">
              {quests.map((quest) => (
                <div
                  className="quest-card"
                  key={quest.id}
                  style={{
                    opacity: quest.completed ? 0.7 : 1,
                    border: quest.completed
                      ? '1.5px solid #2D9E6B'
                      : '1.5px solid transparent'
                  }}>

                  <div className="quest-icon">{quest.icon}</div>

                  <div style={{ flex: 1 }}>
                    <h4>{quest.title}</h4>
                    <p>{quest.description}</p>
                    <div style={{
                      display: 'flex',
                      alignItems: 'center',
                      gap: '8px',
                      marginTop: '6px'
                    }}>
                      <span className="quest-points">
                        ⭐ {quest.points} pts
                      </span>
                      <span style={{
                        fontSize: '11px',
                        fontWeight: '600',
                        color: getDifficultyColor(quest.difficulty),
                        background: `${getDifficultyColor(quest.difficulty)}20`,
                        padding: '2px 8px',
                        borderRadius: '100px'
                      }}>
                        {quest.difficulty}
                      </span>
                      <span style={{
                        fontSize: '11px',
                        color: '#7A9E8A',
                        background: '#D4F5E4',
                        padding: '2px 8px',
                        borderRadius: '100px'
                      }}>
                        {quest.category}
                      </span>
                    </div>
                  </div>

                  <div style={{ marginLeft: '12px' }}>
                    {quest.completed ? (
                      <span style={{
                        background: '#D4F5E4',
                        color: '#1A5C45',
                        padding: '6px 14px',
                        borderRadius: '8px',
                        fontSize: '12px',
                        fontWeight: '700'
                      }}>
                        ✅ Done
                      </span>
                    ) : (
                      <button
                        onClick={() => handleComplete(quest.id)}
                        disabled={completing === quest.id}
                        style={{
                          background: completing === quest.id
                            ? '#7A9E8A'
                            : '#2D9E6B',
                          color: '#FFFFFF',
                          border: 'none',
                          padding: '8px 16px',
                          borderRadius: '8px',
                          fontSize: '12px',
                          fontWeight: '700',
                          cursor: completing === quest.id
                            ? 'not-allowed'
                            : 'pointer'
                        }}>
                        {completing === quest.id
                          ? 'Completing...'
                          : 'Complete'}
                      </button>
                    )}
                  </div>

                </div>
              ))}
            </div>
          )}
        </div>

      </main>
    </div>
  );
}