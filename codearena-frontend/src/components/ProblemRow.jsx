import { useNavigate } from "react-router-dom";

const ProblemRow = ({ problem }) => {
  const navigate = useNavigate();

  return (
    <tr
      style={{ cursor: "pointer" }}
      onClick={() => navigate(`/problems/${problem.problemSlug}`)}
    >
      <td>{problem.id}</td>
      <td className="problem-title">{problem.title}</td>
      <td>
        <span className={`difficulty ${problem.difficulty.toLowerCase()}`}>
          {problem.difficulty}
        </span>
      </td>
      <td>{problem.topics}</td>
    </tr>
  );
};

export default ProblemRow;
