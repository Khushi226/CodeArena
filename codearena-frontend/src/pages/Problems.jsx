import { useEffect, useState } from "react";
import { getAllProblems } from "../services/problemService";
import ProblemsTable from "../components/ProblemsTable";
import "./Problems.css";

const Problems = () => {
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllProblems()
      .then((data) => {
        setProblems(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to fetch problems:", err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div className="problems-page">
        <h1 className="problems-title">Problems</h1>
        <p style={{ textAlign: "center", opacity: 0.6 }}>
          Loading problems...
        </p>
      </div>
    );
  }

  return (
    <div className="problems-page">
      <h1 className="problems-title">Problems</h1>

      <div className="problems-table-wrapper">
        <ProblemsTable problems={problems} />
      </div>
    </div>
  );
};

export default Problems;