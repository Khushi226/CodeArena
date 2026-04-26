const ProblemsFilter = ({ selected, onChange }) => {
  const levels = ["All", "Easy", "Medium", "Hard"];

  return (
    <div className="problems-filter">
      {levels.map(level => (
        <button
          key={level}
          className={selected === level ? "active" : ""}
          onClick={() => onChange(level)}
        >
          {level}
        </button>
      ))}
    </div>
  );
};

export default ProblemsFilter;
