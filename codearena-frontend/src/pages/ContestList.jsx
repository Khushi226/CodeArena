import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllContests } from "../services/contestService";

const TABS = ["RUNNING", "UPCOMING", "ENDED"];
const TAB_LABELS = { RUNNING: "Running", UPCOMING: "Upcoming", ENDED: "Past" };

function formatLocal(isoString) {
  return new Date(isoString).toLocaleString();
}

const ContestList = () => {
  const [contests, setContests] = useState([]);
  const [tab, setTab] = useState("RUNNING");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    getAllContests()
      .then(setContests)
      .catch((err) => console.error("Failed to load contests:", err))
      .finally(() => setLoading(false));
  }, []);

  const filtered = contests.filter((c) => c.status === tab);

  if (loading) return <div className="loading">Loading contests...</div>;

  return (
    <div style={{ maxWidth: 900, margin: "0 auto", padding: "24px 16px" }}>
      <h1>Contests</h1>

      <div style={{ display: "flex", gap: "8px", margin: "16px 0" }}>
        {TABS.map((t) => (
          <button
            key={t}
            onClick={() => setTab(t)}
            style={{
              padding: "8px 16px",
              borderRadius: "6px",
              border: "1px solid #333",
              background: tab === t ? "#22c55e" : "transparent",
              color: tab === t ? "#000" : "#fff",
              cursor: "pointer",
            }}
          >
            {TAB_LABELS[t]}
          </button>
        ))}
      </div>

      {filtered.length === 0 ? (
        <p>No {TAB_LABELS[tab].toLowerCase()} contests right now.</p>
      ) : (
        <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
          {filtered.map((c) => (
            <div
              key={c.id}
              onClick={() => navigate(`/contests/${c.slug}`)}
              style={{
                border: "1px solid #333",
                borderRadius: "8px",
                padding: "16px",
                cursor: "pointer",
              }}
            >
              <h3 style={{ margin: 0 }}>{c.title}</h3>
              <p style={{ margin: "4px 0 0", opacity: 0.7 }}>
                {formatLocal(c.startTime)} &rarr; {formatLocal(c.endTime)}
              </p>
              {c.registered && (
                <span style={{ fontSize: "12px", color: "#22c55e" }}>Registered</span>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ContestList;