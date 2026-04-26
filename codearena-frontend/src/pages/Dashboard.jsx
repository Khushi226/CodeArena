
// import { useEffect, useState } from "react";

// function Dashboard() {

//   const [data, setData] = useState(null);

//   useEffect(() => {

//     const fetchDashboard = async () => {
//       try {

//         const token = localStorage.getItem("token");

//         const response = await fetch(
//           "http://localhost:8080/users/me/dashboard",
//           {
//             headers: {
//               Authorization: "Bearer " + token
//             }
//           }
//         );

//         const result = await response.json();
//         setData(result);

//       } catch (error) {
//         console.error("Dashboard error:", error);
//       }
//     };

//     fetchDashboard();

//   }, []);

//   if (!data) {
//     return <h2 style={{padding:"30px"}}>Loading dashboard...</h2>;
//   }

//   return (
//     <div style={{padding:"40px"}}>

//       <h1>Hey {data.username}</h1>

//       <h2 style={{marginTop:"20px"}}>
//         Questions Solved: {data.totalSolved}
//       </h2>

//       <div style={{
//         display:"flex",
//         gap:"20px",
//         marginTop:"30px"
//       }}>

//         <div style={{
//           background:"#e8f8ee",
//           padding:"20px",
//           borderRadius:"10px",
//           width:"150px",
//           textAlign:"center"
//         }}>
//           <h3>Easy</h3>
//           <h2>{data.easySolved}</h2>
//         </div>

//         <div style={{
//           background:"#fff4cc",
//           padding:"20px",
//           borderRadius:"10px",
//           width:"150px",
//           textAlign:"center"
//         }}>
//           <h3>Medium</h3>
//           <h2>{data.mediumSolved}</h2>
//         </div>

//         <div style={{
//           background:"#ffe6e6",
//           padding:"20px",
//           borderRadius:"10px",
//           width:"150px",
//           textAlign:"center"
//         }}>
//           <h3>Hard</h3>
//           <h2>{data.hardSolved}</h2>
//         </div>

//       </div>

//     </div>
//   );
// }

// export default Dashboard;










import { useEffect, useState } from "react";
import "./Dashboard.css";

function Dashboard() {

  const [data, setData] = useState(null);

  useEffect(() => {

    const fetchDashboard = async () => {
      try {

        const token = localStorage.getItem("token");

        const response = await fetch(
          "http://localhost:8080/users/me/dashboard",
          {
            headers: {
              Authorization: "Bearer " + token
            }
          }
        );

        const result = await response.json();
        setData(result);

      } catch (error) {
        console.error("Dashboard error:", error);
      }
    };

    fetchDashboard();

  }, []);

  if (!data) {
    return <h2 className="loading">Loading dashboard...</h2>;
  }

  return (
    <div className="dashboard-container">

      <h1 className="dashboard-title">
        Hey {data.username}
      </h1>

      {/* TOTAL SOLVED CARD */}
      <div className="total-card">

        <div className="circle">
          {data.totalSolved}
        </div>

        <p>Total Solved</p>

      </div>

      {/* DIFFICULTY CARDS */}
      <div className="difficulty-cards">

        <div className="difficulty-card easy">
          <h3>Easy</h3>
          <h2>{data.easySolved}</h2>
        </div>

        <div className="difficulty-card medium">
          <h3>Medium</h3>
          <h2>{data.mediumSolved}</h2>
        </div>

        <div className="difficulty-card hard">
          <h3>Hard</h3>
          <h2>{data.hardSolved}</h2>
        </div>

      </div>

    </div>
  );
}

export default Dashboard;