import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getHiddenProblemsForAdmin } from "../services/problemService";
import { createContest } from "../services/contestService";

const CreateContest = () => {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  const [problems, setProblems] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startLocal, setStartLocal] = useState("");
  const [endLocal, setEndLocal] = useState("");
  // problemId -> { selected, points, orderIndex }
  const [selection, setSelection] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (role !== "ADMIN") return;
    getHiddenProblemsForAdmin()
      .then(setProblems)
      .catch((err) => console.error("Failed to load problems:", err));
  }, [role]);

  // Client-side gate only — this is UX, not security. The backend's
  // POST /contests endpoint independently enforces hasRole("ADMIN") and
  // will reject the request regardless of what this page shows.
  if (role !== "ADMIN") {
    return (
      <div style={{ maxWidth: 600, margin: "40px auto", padding: "0 16px" }}>
        <h2>Admins only</h2>
        <p>You don't have permission to create a contest.</p>
      </div>
    );
  }

  function toggleProblem(problemId) {
    setSelection((prev) => {
      const existing = prev[problemId];
      if (existing?.selected) {
        const next = { ...prev };
        delete next[problemId];
        return next;
      }
      const nextOrderIndex = Object.keys(prev).length;
      return {
        ...prev,
        [problemId]: { selected: true, points: 100, orderIndex: nextOrderIndex },
      };
    });
  }

  function updatePoints(problemId, points) {
    setSelection((prev) => ({
      ...prev,
      [problemId]: { ...prev[problemId], points: Number(points) },
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    const selectedProblems = Object.entries(selection).map(([problemId, v]) => ({
      problemId: Number(problemId),
      points: v.points,
      orderIndex: v.orderIndex,
    }));

    if (selectedProblems.length === 0) {
      setError("Select at least one problem.");
      return;
    }
    if (!startLocal || !endLocal) {
      setError("Set both a start and end time.");
      return;
    }

    // datetime-local values have no timezone info and are interpreted by
    // the browser as local time. new Date(...) parses them as local time,
    // and toISOString() converts that to UTC — which is exactly what the
    // backend expects for Contest.startTime/endTime.
    const startTime = new Date(startLocal).toISOString();
    const endTime = new Date(endLocal).toISOString();

    if (new Date(endTime) <= new Date(startTime)) {
      setError("End time must be after start time.");
      return;
    }

    setSubmitting(true);
    try {
      const contest = await createContest({
        title,
        description,
        startTime,
        endTime,
        problems: selectedProblems,
      });
      navigate(`/contests/${contest.slug}`);
    } catch (err) {
      setError(err.message);
    }
    setSubmitting(false);
  }

  return (
    <div style={{ maxWidth: 700, margin: "0 auto", padding: "24px 16px" }}>
      <h1>Create Contest</h1>

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "12px" }}>
          <label>Title</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            style={{ width: "100%", padding: "8px" }}
          />
        </div>

        <div style={{ marginBottom: "12px" }}>
          <label>Description</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={3}
            style={{ width: "100%", padding: "8px" }}
          />
        </div>

        <div style={{ display: "flex", gap: "16px", marginBottom: "12px" }}>
          <div style={{ flex: 1 }}>
            <label>Start (your local time)</label>
            <input
              type="datetime-local"
              value={startLocal}
              onChange={(e) => setStartLocal(e.target.value)}
              required
              style={{ width: "100%", padding: "8px" }}
            />
          </div>
          <div style={{ flex: 1 }}>
            <label>End (your local time)</label>
            <input
              type="datetime-local"
              value={endLocal}
              onChange={(e) => setEndLocal(e.target.value)}
              required
              style={{ width: "100%", padding: "8px" }}
            />
          </div>
        </div>

        <h3>Problems</h3>
        <div style={{ maxHeight: "300px", overflowY: "auto", border: "1px solid #333", borderRadius: "6px" }}>
          {problems.map((p) => {
            const sel = selection[p.id];
            return (
              <div
                key={p.id}
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: "12px",
                  padding: "8px 12px",
                  borderBottom: "1px solid #222",
                }}
              >
                <input
                  type="checkbox"
                  checked={!!sel?.selected}
                  onChange={() => toggleProblem(p.id)}
                />
                <span style={{ flex: 1 }}>{p.title}</span>
                {sel?.selected && (
                  <input
                    type="number"
                    value={sel.points}
                    onChange={(e) => updatePoints(p.id, e.target.value)}
                    style={{ width: "80px" }}
                    min={1}
                  />
                )}
              </div>
            );
          })}
        </div>

        {error && <p style={{ color: "#f87171", marginTop: "12px" }}>{error}</p>}

        <button type="submit" disabled={submitting} style={{ marginTop: "16px" }}>
          {submitting ? "Creating..." : "Create Contest"}
        </button>
      </form>
    </div>
  );
};

export default CreateContest;