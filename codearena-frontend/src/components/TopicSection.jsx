import { useState } from "react";
import TopicCard from "./TopicCard";
import { useNavigate } from "react-router-dom";

function TopicsSection() {
  const [selectedTopic, setSelectedTopic] = useState(null);
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const topics = [
    { title: "Arrays", color: "green", slug: "ARRAY" },
    { title: "Strings", color: "red", slug: "STRING" },
    {
      title: "Dynamic Programming",
      // count: "245 problems",
      color: "orange",
      slug: "dynamic-programming",
    },
    { title: "Trees",  color: "green", slug: "BINARY_TREE" },
    { title: "Graphs", color: "yellow", slug: "graphs" },
    { title: "Linked List", color: "green", slug: "LINKED_LIST" },
  ];

  const handleTopicClick = async (slug) => {
    setSelectedTopic(slug);
    setLoading(true);

    try {
      const res = await fetch(
        `http://localhost:8080/problems/topic/${slug}`
      );
      const data = await res.json();
      setProblems(data);
    } catch (err) {
      console.error("Error fetching problems:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="topics-section container">
      {/* Header */}
      <div className="topics-header">
        <div>
          <h2>Explore by Topic</h2>
          <p>Choose your preferred category to start practicing</p>
        </div>
      </div>

      {/* Topics Row */}
      <div className="topics-grid">
        {topics.map((topic) => (
          <TopicCard
            key={topic.slug}
            title={topic.title}
            count={topic.count}
            color={topic.color}
            onClick={() => navigate(`/topic/${topic.slug}`)}
            isActive={selectedTopic === topic.slug}
          />
        ))}
      </div>

      {/* Problems Section */}
      <div className="problems-section">
        {loading ? (
          <p>Loading problems...</p>
        ) : problems.length > 0 ? (
          problems.map((p) => (
            <div key={p.id} className="card">
              <h3>{p.title}</h3>
              <p>{p.difficulty}</p>
            </div>
          ))
        ) : selectedTopic ? (
          <p>No problems found for this topic.</p>
        ) : (
          <p>Select a topic to view problems.</p>
        )}
      </div>
    </div>
  );
}

export default TopicsSection;