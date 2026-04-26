// function TopicCard({ title, count, color }) {
//   return (
//     <div className={`topic-card ${color}`}>
//       <div className="topic-icon"></div>
//       <h4>{title}</h4>
//       <p>{count}</p>
//     </div>
//   );
// }




// function TopicCard({ title, count, color, onClick, isActive }) {
//   return (
//     <div
//       className={`topic-card ${color} ${isActive ? "active" : ""}`}
//       onClick={onClick}
//     >
//       <div className="topic-icon"></div>
//       <h4>{title}</h4>
//       <p>{count}</p>
//     </div>
//   );
// }

// export default TopicCard;






function TopicCard({ title, count, color, onClick, isActive }) {
  return (
    <div
      className={`topic-card ${color} ${isActive ? "active" : ""}`}
      onClick={onClick}
    >
      <div className="topic-icon"></div>
      <h4>{title}</h4>
      <p>{count}</p>
    </div>
  );
}

export default TopicCard;