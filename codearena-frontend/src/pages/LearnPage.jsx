
// import { useEffect, useState } from "react";
// import { Link } from "react-router-dom";
// import './LearnPage.css';

// const LearnPage = () => {

//   const [problems, setProblems] = useState([]);
//   const [selectedTopic, setSelectedTopic] = useState("All");

//   useEffect(() => {
//     fetch("http://localhost:8080/api/learn/problems")
//       .then(res => res.json())
//       .then(data => {
//         console.log("Problems from API:", data);
//         setProblems(data);
//       })
//       .catch(err => console.error(err));
//   }, []);

//   const topics = ["All", ...new Set(problems.map(p => p.topic))];

//   const filteredProblems =
//     selectedTopic === "All"
//       ? problems
//       : problems.filter(p => p.topic === selectedTopic);

//   return (
//     <div className="learn-page">

//       <h1>Learn DSA</h1>

//       {/* Topics */}
//       <div className="topics">
//         {topics.map(topic => (
//           <button
//             key={topic}
//             onClick={() => setSelectedTopic(topic)}
//           >
//             {topic}
//           </button>
//         ))}
//       </div>

//       {/* Problems */}
//       <div className="problems-list">
//         {filteredProblems.map(problem => (
//           <Link
//             key={problem.slug}
//             to={`/learn/${problem.slug}`}
//             className="problem-card"
//           >
//             {problem.title}
//           </Link>
//         ))}
//       </div>

//     </div>
//   );
// };

// export default LearnPage;











import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./LearnPage.css";

const LearnPage = () => {
  const [problems, setProblems] = useState([]);
  const [selectedTopic, setSelectedTopic] = useState("All");

  useEffect(() => {
    fetch("http://localhost:8080/api/learn/problems")
      .then((res) => res.json())
      .then((data) => {
        console.log("Problems from API:", data);
        setProblems(data);
      })
      .catch((err) => console.error(err));
  }, []);

  // Get unique topics
  const topics = ["All", ...new Set(problems.map((p) => p.topics))];

  // Filter problems
  const filteredProblems =
    selectedTopic === "All"
      ? problems
      : problems.filter((p) => p.topic === selectedTopic);

  return (
    <div className="learn-page">
      <h1>Learn DSA</h1>

      {/* 🔘 Topic Filters */}
      <div className="topics">
        {topics.map((topic) => (
          <button
            key={topic}
            className={selectedTopic === topic ? "active" : ""}
            onClick={() => setSelectedTopic(topic)}
          >
            {topic}
          </button>
        ))}
      </div>

      {/* 📊 TABLE */}
      <div className="problems-table">
        {/* Header */}
        <div className="table-header">
          {/* <span>Status</span> */}
          <span>Title</span>
          <span>Difficulty</span>
          <span>Topic</span>
        </div>

        {/* Rows */}
        {filteredProblems.map((problem) => (
          <Link
            key={problem.slug}
            to={`/learn/${problem.slug}`}
            className="table-row"
          >
            {/* You can later replace ○ with ✓ if solved */}
            {/* <span className="status">○</span> */}
            {/* <span
              className={`status ${
                problem.status === "SOLVED"
                  ? "solved"
                  : problem.status === "ATTEMPTED"
                  ? "attempted"
                  : "unsolved"
              }`}
            >
              {problem.status === "SOLVED"
                ? "✓"
                : problem.status === "ATTEMPTED"
                ? "⏳"
                : "○"}
            </span> */}

            <span className="title">{problem.title}</span>

            {/* <span
              className={`difficulty ${problem.difficulty.toLowerCase()}`}
            >
              {problem.difficulty}
            </span> */}
            {/* <span className={`difficulty ${(problem.difficulty || "easy").toLowerCase()}`}>
  {problem.difficulty || "Easy"}
</span> */}

<span className={`difficulty ${(problem.difficulty || "easy").toLowerCase()}`}>
  {problem.difficulty || "Easy"}
</span>

            <span className="topic">{problem.topic}</span>
          </Link>
        ))}

        {/* Empty state */}
        {filteredProblems.length === 0 && (
          <div className="no-data">
            No problems found for this topic.
          </div>
        )}
      </div>
    </div>
  );
};

export default LearnPage;