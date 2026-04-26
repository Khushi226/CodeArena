// import { useEffect, useState } from "react";
// import "./Submissions.css";

// function Submissions() {

//   const [submissions, setSubmissions] = useState([]);

//   useEffect(() => {

//     const token = localStorage.getItem("token");
//     const userId = localStorage.getItem("userId");

//     if (!userId) {
//       console.error("User ID not found in localStorage");
//       return;
//     }

//     fetch(`http://localhost:8080/submissions/user/${userId}`, {
//       headers: {
//         Authorization: "Bearer " + token
//       }
//     })
//       .then(res => res.json())
//       .then(data => setSubmissions(data))
//       .catch(err => console.error("Error fetching submissions:", err));

//   }, []);

//   return (
//     <div className="submissions-container">

//       <h1>My Submissions</h1>

//       <table className="submissions-table">

//         <thead>
//           <tr>
//             <th>Problem ID</th>
//             <th>Language</th>
//             <th>Verdict</th>
//             <th>Date</th>
//           </tr>
//         </thead>

//         <tbody>

//           {submissions.length === 0 ? (
//             <tr>
//               <td colSpan="4">No submissions yet</td>
//             </tr>
//           ) : (

//             submissions.map((sub) => (

//               <tr key={sub.id}>

//                 <td>{sub.problemId}</td>

//                 <td>{sub.language}</td>

//                 <td
//                   className={
//                     sub.verdict === "Accepted"
//                       ? "verdict-accepted"
//                       : "verdict-wrong"
//                   }
//                 >
//                   {sub.verdict}
//                 </td>

//                 <td>
//                   {new Date(sub.createdAt).toLocaleString()}
//                 </td>

//               </tr>

//             ))

//           )}

//         </tbody>

//       </table>

//     </div>
//   );
// }

// export default Submissions;















import { useEffect, useState } from "react";
import "./Submissions.css";

function Submissions() {

  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {

    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    console.log("UserId:", userId);
    console.log("Token:", token);

    if (!userId || !token) {
      console.error("User not logged in");
      return;
    }

    fetch(`http://localhost:8080/submissions/user/${userId}`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
        "Content-Type": "application/json"
      }
    })
      .then(res => {
        if (!res.ok) {
          throw new Error("Failed to fetch submissions");
        }
        return res.json();
      })
      .then(data => {
        console.log("SUBMISSIONS DATA:", data);
        setSubmissions(data);
      })
      .catch(err => {
        console.error("Error fetching submissions:", err);
      });

  }, []);

  return (
    <div className="submissions-container">

      <h1>My Submissions</h1>

      <table className="submissions-table">

        <thead>
          <tr>
            <th>Problem ID</th>
            <th>Language</th>
            <th>Verdict</th>
            <th>Date</th>
          </tr>
        </thead>

        <tbody>

          {submissions.length === 0 ? (

            <tr>
              <td colSpan="4">No submissions yet</td>
            </tr>

          ) : (

            submissions.map((sub) => (

              <tr key={sub.id}>

                <td>{sub.problemId}</td>

                <td>{sub.language}</td>

                <td
                  className={
                    sub.verdict === "ACCEPTED"
                      ? "verdict-accepted"
                      : "verdict-wrong"
                  }
                >
                  {sub.verdict}
                </td>

                <td>
                  {sub.createdAt
                    ? new Date(sub.createdAt).toLocaleString()
                    : "-"}
                </td>

              </tr>

            ))

          )}

        </tbody>

      </table>

    </div>
  );
}

export default Submissions;