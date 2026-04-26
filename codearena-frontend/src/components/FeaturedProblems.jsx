// import "./FeaturedProblems.css";
// import { useNavigate } from "react-router-dom";

// function FeaturedProblems() {

//   const navigate = useNavigate();

//   // const problems = [
//   //   { id:1, title:"Two Sum", topic:"Arrays", difficulty:"Easy", acceptance:"52.3%" },
//   //   { id:2, title:"Add Two Numbers", topic:"Linked List", difficulty:"Medium", acceptance:"41.7%" },
//   //   { id:3, title:"Longest Substring Without Repeating Characters", topic:"Strings", difficulty:"Medium", acceptance:"34.2%" },
//   //   { id:4, title:"Median of Two Sorted Arrays", topic:"Binary Search", difficulty:"Hard", acceptance:"38.1%" },
//   //   { id:5, title:"Longest Palindromic Substring", topic:"Dynamic Programming", difficulty:"Medium", acceptance:"32.8%" }
//   // ];

//   const problems = [
//   { id:1, title:"Two Sum", slug:"two-sum", topic:"Arrays", difficulty:"Easy", acceptance:"52.3%" },
//   { id:2, title:"Add Two Numbers", slug:"add-two-numbers", topic:"Linked List", difficulty:"Medium", acceptance:"41.7%" },
//   { id:3, title:"Longest Substring Without Repeating Characters", slug:"longest-substring-without-repeating-characters", topic:"Strings", difficulty:"Medium", acceptance:"34.2%" },
//   { id:4, title:"Median of Two Sorted Arrays", slug:"median-of-two-sorted-arrays", topic:"Binary Search", difficulty:"Hard", acceptance:"38.1%" },
//   { id:5, title:"Longest Palindromic Substring", slug:"longest-palindromic-substring", topic:"Dynamic Programming", difficulty:"Medium", acceptance:"32.8%" }
// ];

//   return (
//     <section className="featured-section container">

//       <div className="featured-header">

//         <div>
//           <h2>Featured Problems</h2>
//           <p>Popular challenges loved by the community</p>
//         </div>

//         <button
//           className="view-problems-btn"
//           onClick={()=>navigate("/problems")}
//         >
//           View All Problems →
//         </button>

//       </div>


//       <div className="featured-list">

//         {problems.map((p,index)=>(
//           <div
//             key={p.id}
//             className="problem-row"
//             // onClick={()=>navigate(`/problems/${p.id}`)}
//             onClick={()=>navigate(`/problems/problem/${p.slug}`)}
//           >

//             <div className="problem-left">
//               <span className="problem-number">{index+1}.</span>

//               <div>
//                 <h4>{p.title}</h4>
//                 <span className="problem-topic">{p.topic}</span>
//               </div>
//             </div>


//             <div className="problem-right">

//               <span className="acceptance">
//                 {p.acceptance}
//               </span>

//               <span className={`difficulty ${p.difficulty.toLowerCase()}`}>
//                 {p.difficulty}
//               </span>

//             </div>

//           </div>
//         ))}

//       </div>

//     </section>
//   );
// }

// export default FeaturedProblems;

















import "./FeaturedProblems.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

function FeaturedProblems() {

  const navigate = useNavigate();
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/problems")
      .then(res => res.json())
      .then(data => {
        console.log("Problems:", data);

        // take first 5 problems as featured
        setProblems(data.slice(0, 5));
      })
      .catch(err => console.error("Error fetching problems:", err));
  }, []);

  return (
    <section className="featured-section container">

      <div className="featured-header">
        <div>
          <h2>Featured Problems</h2>
          <p>Popular challenges loved by the community</p>
        </div>

        <button
          className="view-problems-btn"
          onClick={() => navigate("/problems")}
        >
          View All Problems →
        </button>
      </div>

      <div className="featured-list">

        {problems.length === 0 ? (
          <p style={{ color: "white" }}>Loading...</p>
        ) : (
          problems.map((p, index) => (
            <div
              key={p.id}
              className="problem-row"
              onClick={() => navigate(`/problems/${p.problemSlug}`)}
            >

              <div className="problem-left">
                <span className="problem-number">{index + 1}.</span>

                <div>
                  <h4>{p.title}</h4>
                  <span className="problem-topic">
                    {p.topics || "General"}
                  </span>
                </div>
              </div>

              <div className="problem-right">

                <span className="acceptance">
                  {/* optional for now */}
                  --
                </span>

                <span className={`difficulty ${p.difficulty.toLowerCase()}`}>
                  {p.difficulty}
                </span>

              </div>

            </div>
          ))
        )}

      </div>

    </section>
  );
}

export default FeaturedProblems;