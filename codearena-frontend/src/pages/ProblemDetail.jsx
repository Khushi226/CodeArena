import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getProblemBySlug } from "../services/problemService";
import CodeEditor from "../components/CodeEditor";
import "./ProblemDetail.css";

const ProblemDetail = () => {
  const { slug } = useParams();
  const [problem, setProblem] = useState(null);

  useEffect(() => {
    getProblemBySlug(slug).then(setProblem);
  }, [slug]);

  if (!problem) return <div className="loading">Loading...</div>;

  return (
    <div className="problem-page">
      {/* LEFT PANEL */}
      <div className="problem-left">
        <div className="problem-header">
        <h1 className="problem-title">
          {problem.title}
          </h1>
          {/* <span className={`difficulty-badge ${problem.difficulty.toLowerCase()}`}>
            {problem.difficulty}
          </span> */}
          <span className={`difficulty-badge ${problem.difficulty?.toLowerCase() || ""}`}>
            {problem.difficulty || ""}
          </span>
        
        </div>

        <p className="problem-description">{problem.description}</p>

        {/* <h3>Examples</h3>
        {renderExamples(problem.examples)} */}
        <h3 className="section-title">Examples</h3>
        <div className="examples-container">
          {renderExamples(problem.examples)}
        </div>

        {/* <h3>Constraints</h3> */}
        <h3 className="section-title">Constraints</h3>
        <div className="constraints">{problem.constraints}</div>
      </div>

      {/* RIGHT PANEL */}
      <div className="problem-right">
        <CodeEditor problemId={problem.id} starterCode={problem.starterCode} />
      </div>
    </div>
  );
};

const renderExamples = (examples) => {
  try {
    const parsed = JSON.parse(examples);
    return parsed.map((ex, i) => (
      <pre key={i} className="example-box">
        <b>Example {ex.example_num}</b>
        {"\n"}
        {ex.example_text}
      </pre>
    ));
  } catch {
    return <pre className="example-box">{examples}</pre>;
  }
};

export default ProblemDetail;
