import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

function ProblemsByTopic() {
  const { topic } = useParams();
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchProblems = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/problems/topic/${topic}`
        );
        const data = await res.json();
        setProblems(data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProblems();
  }, [topic]);

  return (
    <div className="topic-page">
      <h1 className="topic-title">{topic.toUpperCase()} Problems</h1>

      {loading ? (
        <p>Loading...</p>
      ) : (
        <div className="problem-grid">
          {problems.map((p) => (
            <div
              key={p.id}
              className="problem-card-new"
              onClick={() => navigate(`/problems/${p.problemSlug}`)}
            >
              <h3>{p.title}</h3>

              <span className={`difficulty ${p.difficulty.toLowerCase()}`}>
                {p.difficulty}
              </span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default ProblemsByTopic;