import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getContestBySlug, getLeaderboard } from "../services/contestService";

const Leaderboard = () => {
  const { slug } = useParams();
  const [contestId, setContestId] = useState(null);
  const [standings, setStandings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getContestBySlug(slug).then(({ contest }) => setContestId(contest.id));
  }, [slug]);

  useEffect(() => {
    if (!contestId) return;

    let cancelled = false;

    function poll() {
      getLeaderboard(contestId)
        .then((data) => {
          if (!cancelled) setStandings(data);
        })
        .catch((err) => console.error("Failed to load leaderboard:", err))
        .finally(() => {
          if (!cancelled) setLoading(false);
        });
    }

    poll();
    const interval = setInterval(poll, 10000);
    return () => {
      cancelled = true;
      clearInterval(interval);
    };
  }, [contestId]);

  if (loading) return <div className="loading">Loading leaderboard...</div>;

  return (
    <div style={{ maxWidth: 700, margin: "0 auto", padding: "24px 16px" }}>
      <h1>Leaderboard</h1>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ textAlign: "left" }}>Rank</th>
            <th style={{ textAlign: "left" }}>User</th>
            <th style={{ textAlign: "left" }}>Score</th>
          </tr>
        </thead>
        <tbody>
          {standings.map((s) => (
            <tr key={s.userId}>
              <td>{s.rank}</td>
              <td>{s.username}</td>
              <td>{s.totalScore}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {standings.length === 0 && <p>No one has scored yet.</p>}
    </div>
  );
};

export default Leaderboard;