import { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getContestBySlug, registerForContest } from "../services/contestService";

function formatDuration(ms) {
  if (ms <= 0) return "00:00:00";
  const totalSeconds = Math.floor(ms / 1000);
  const h = String(Math.floor(totalSeconds / 3600)).padStart(2, "0");
  const m = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, "0");
  const s = String(totalSeconds % 60).padStart(2, "0");
  return `${h}:${m}:${s}`;
}

const ContestDetail = () => {
  const { slug } = useParams();
  const navigate = useNavigate();
  const [contest, setContest] = useState(null);
  // offsetMs = serverTime - clientTime at load. Adding it to Date.now() at any
  // later point gives a corrected "now" even if the user's clock is wrong.
  const offsetRef = useRef(0);
  const [now, setNow] = useState(Date.now());
  const [registering, setRegistering] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    getContestBySlug(slug).then(({ contest, serverTimeMs }) => {
      setContest(contest);
      offsetRef.current = serverTimeMs - Date.now();
    });
  }, [slug]);

  useEffect(() => {
    const interval = setInterval(() => setNow(Date.now() + offsetRef.current), 1000);
    return () => clearInterval(interval);
  }, []);

  if (!contest) return <div className="loading">Loading contest...</div>;

  const startMs = new Date(contest.startTime).getTime();
  const endMs = new Date(contest.endTime).getTime();
  const status = now < startMs ? "UPCOMING" : now > endMs ? "ENDED" : "RUNNING";

  async function handleRegister() {
    setRegistering(true);
    setError(null);
    try {
      await registerForContest(contest.id);
      setContest({ ...contest, registered: true });
      if (status === "RUNNING") {
        navigate(`/contests/${slug}/arena`);
      }
    } catch (err) {
      setError(err.message);
    }
    setRegistering(false);
  }

  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: "24px 16px" }}>
      <h1>{contest.title}</h1>
      <p style={{ opacity: 0.8 }}>{contest.description}</p>

      <p>
        <strong>Starts:</strong> {new Date(contest.startTime).toLocaleString()}
        <br />
        <strong>Ends:</strong> {new Date(contest.endTime).toLocaleString()}
      </p>

      <h3>Problems</h3>
      <ul>
        {contest.problems.map((p) => (
          <li key={p.problemId}>
            {p.title} — {p.points} pts
          </li>
        ))}
      </ul>

      {error && <p style={{ color: "#f87171" }}>{error}</p>}

      {status === "UPCOMING" && (
        <>
          <div style={{ fontSize: "24px", margin: "16px 0" }}>
            Starts in {formatDuration(startMs - now)}
          </div>
          {!contest.registered && (
            <button onClick={handleRegister} disabled={registering}>
              {registering ? "Registering..." : "Register"}
            </button>
          )}
          {contest.registered && <p style={{ color: "#22c55e" }}>You're registered.</p>}
        </>
      )}

      {status === "RUNNING" && (
        <>
          <div style={{ fontSize: "24px", margin: "16px 0" }}>
            Ends in {formatDuration(endMs - now)}
          </div>
          {contest.registered ? (
            <button onClick={() => navigate(`/contests/${slug}/arena`)}>
              Enter Contest
            </button>
          ) : (
            <button onClick={handleRegister} disabled={registering}>
              {registering ? "Registering..." : "Register & Enter"}
            </button>
          )}
        </>
      )}

      {status === "ENDED" && (
        <>
          <p>This contest has ended.</p>
          <button onClick={() => navigate(`/contests/${slug}/leaderboard`)}>
            View Leaderboard
          </button>
        </>
      )}
    </div>
  );
};

export default ContestDetail;