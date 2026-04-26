// import { useEffect, useState } from "react";
// import "./StatsCards.css";

// export default function StatsCards() {

//   // 🔹 MOCK DATA (replace later with backend)
//   const [stats, setStats] = useState({
//     totalProblems: 3248,
//     // activeContests: 5,
//     submissionsToday: 12,
//     successRate: 67,
//   });

//   // 🔹 Simulate future backend call
//   useEffect(() => {
//     // later you will replace this with fetch()
//     // fetch("/api/dashboard/stats").then(...)
//   }, []);

//   return (
//     <div className="stats-grid">

//       <div className="stat-card">
//         <div className="stat-icon">&lt;/&gt;</div>
//         <h2 className="stat-value">{stats.totalProblems}</h2>
//         <p className="stat-label">Total Problems</p>
//       </div>

//       {/* <div className="stat-card">
//         <div className="stat-icon">🏆</div>
//         <h2 className="stat-value">{stats.activeContests}</h2>
//         <p className="stat-label">Active Contests</p>
//       </div> */}

//       <div className="stat-card">
//         <div className="stat-icon">⚡</div>
//         <h2 className="stat-value">{stats.submissionsToday}</h2>
//         <p className="stat-label">Submissions Today</p>
//       </div>

//       <div className="stat-card">
//         <div className="stat-icon">📈</div>
//         <h2 className="stat-value">{stats.successRate}%</h2>
//         <p className="stat-label">Success Rate</p>
//       </div>

//     </div>
//   );
// }
