import ProblemRow from "./ProblemRow";

const ProblemsTable = ({ problems }) => {
  return (
    <table className="problems-table">
      <thead>
        <tr>
          <th>#</th>
          <th>Title</th>
          <th>Difficulty</th>
          <th>Topics</th>
        </tr>
      </thead>
      <tbody>
        {problems.map(problem => (
          <ProblemRow key={problem.id} problem={problem} />
        ))}
      </tbody>
    </table>
  );
};

export default ProblemsTable;
