import { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CodeEditor from "../components/CodeEditor";
import {
  getContestBySlug,
  getContestProblem,
  getContestSubmissions,
  submitContestSolution,
} from "../services/contestService";

function formatDuration(ms) {
  if (ms <= 0) return "00:00:00";
  const totalSeconds = Math.floor(ms / 1000);
  const h = String(Math.floor(totalSeconds / 3600)).padStart(2, "0");
  const m = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, "0");
  const s = String(totalSeconds % 60).padStart(2, "0");
  return `${h}:${m}:${s}`;
}

const ContestArena = () => {
  const { slug } = useParams();
  const navigate = useNavigate();

  const [contest, setContest] = useState(null);
  const offsetRef = useRef(0);
  const [now, setNow] = useState(Date.now());

  const [selectedProblemId, setSelectedProblemId] = useState(null);
  const [problemDetail, setProblemDetail] = useState(null);
  const [loadingProblem, setLoadingProblem] = useState(false);

  // Load contest metadata + server time offset
  useEffect(() => {
    getContestBySlug(slug).then(({ contest, serverTimeMs }) => {
      setContest(contest);
      offsetRef.current = serverTimeMs - Date.now();

      // Redirect out if this contest isn't actually running right now —
      // the backend will reject requests anyway, but this avoids a confusing
      // "hasn't started"/"has ended" error screen inside the editor.
      const startMs = new Date(contest.startTime).getTime();
      const endMs = new Date(contest.endTime).getTime();
      const correctedNow = serverTimeMs;
      if (correctedNow < startMs || correctedNow > endMs) {
        navigate(`/contests/${slug}`, { replace: true });
        return;
      }

      if (contest.problems.length > 0) {
        setSelectedProblemId(contest.problems[0].problemId);
      }
    });
  }, [slug, navigate]);

  useEffect(() => {
    const interval = setInterval(() => setNow(Date.now() + offsetRef.current), 1000);
    return () => clearInterval(interval);
  }, []);

  // Load the selected problem's statement/starter code
  useEffect(() => {
    if (!contest || !selectedProblemId) return;
    setLoadingProblem(true);
    getContestProblem(contest.id, selectedProblemId)
      .then(setProblemDetail)
      .catch((err) => {
        console.error(err);
        setProblemDetail(null);
      })
      .finally(() => setLoadingProblem(false));
  }, [contest, selectedProblemId]);

  if (!contest) return <div className="loading">Loading contest...</div>;

  const endMs = new Date(contest.endTime).getTime();
  const remainingMs = endMs - now;

  if (remainingMs <= 0) {
    // Contest ended while the user was sitting in the arena.
    navigate(`/contests/${slug}/leaderboard`, { replace: true });
    return null;
  }

  return (
    <div style={{ display: "flex", height: "calc(100vh - 64px)" }}>
      {/* Problem sidebar */}
      <div style={{ width: 220, borderRight: "1px solid #333", padding: "12px" }}>
        <div style={{ fontSize: "20px", marginBottom: "12px", fontFamily: "monospace" }}>
          {formatDuration(remainingMs)}
        </div>
        {contest.problems.map((p, i) => (
          <div
            key={p.problemId}
            onClick={() => setSelectedProblemId(p.problemId)}
            style={{
              padding: "8px",
              marginBottom: "4px",
              borderRadius: "6px",
              cursor: "pointer",
              background: selectedProblemId === p.problemId ? "#22c55e33" : "transparent",
            }}
          >
            {String.fromCharCode(65 + i)}. {p.title}
            <div style={{ fontSize: "12px", opacity: 0.6 }}>{p.points} pts</div>
          </div>
        ))}
        <button
          style={{ marginTop: "16px", width: "100%" }}
          onClick={() => navigate(`/contests/${slug}/leaderboard`)}
        >
          Leaderboard
        </button>
      </div>

      {/* Problem + editor */}
      <div style={{ flex: 1, display: "flex", overflow: "hidden" }}>
        {loadingProblem || !problemDetail ? (
          <div className="loading">Loading problem...</div>
        ) : (
          <>
            <div style={{ width: "40%", padding: "16px", overflowY: "auto" }}>
              <h2>{problemDetail.title}</h2>
              <p style={{ whiteSpace: "pre-wrap" }}>{problemDetail.description}</p>
              <h4>Constraints</h4>
              <pre>{problemDetail.constraints}</pre>
            </div>

            <div style={{ flex: 1, padding: "16px", overflowY: "auto" }}>
              {/* key={selectedProblemId} forces a full remount when switching
                  problems, so a slow Run/Submit for problem A can't land its
                  result on problem B's screen after navigating between them. */}
              <CodeEditor
                key={selectedProblemId}
                problemId={selectedProblemId}
                starterCode={problemDetail.starterCode}
                storageKey={`contest-${contest.id}-${selectedProblemId}`}
                submitFn={(problemId, language, code) =>
                  submitContestSolution(contest.id, problemId, language, code)
                }
                loadSubmissionsFn={(problemId) =>
                  getContestSubmissions(contest.id, problemId)
                }
              />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default ContestArena;