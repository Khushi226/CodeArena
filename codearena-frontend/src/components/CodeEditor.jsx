// // import Editor from "@monaco-editor/react";
// // import { useEffect, useState } from "react";
// // import "./CodeEditor.css";
// // import { runCode, submitCode, getSubmissions } from "../services/judgeService";


// // // console.log("StarterCode inside CodeEditor:", starterCode);

// // const CodeEditor = ({ problemId, starterCode }) => {
// //   const [language, setLanguage] = useState("java");
// //   const [code, setCode] = useState("");

// //   const [result, setResult] = useState(null);
// //   const [submissions, setSubmissions] = useState([]);
// //   const [loading, setLoading] = useState(false);

// //   useEffect(() => {
// //     if (starterCode && language === "java") {
// //       setCode(starterCode);
// //     }
// //   }, [starterCode, language]);

// //   async function loadSubmissionHistory() {
// //     if (!problemId) return;
// //     const list = await getSubmissions(problemId);
// //     setSubmissions(list);
// //   }

// //   useEffect(() => {
// //     loadSubmissionHistory();
// //   }, [problemId]);

// //   async function handleRun() {
// //     setLoading(true);
// //     setResult(null);

// //     try {
// //       const data = await runCode(problemId, language, code);
// //       setResult(data);
// //     } catch (err) {
// //       setResult({ result: "SERVER_ERROR", message: err.message });
// //     }

// //     setLoading(false);
// //   }

// //   async function handleSubmit() {
// //     setLoading(true);
// //     setResult(null);

// //     try {
// //       const data = await submitCode(problemId, language, code);
// //       setResult(data);
// //       await loadSubmissionHistory();
// //     } catch (err) {
// //       setResult({ result: "SERVER_ERROR", message: err.message });
// //     }

// //     setLoading(false);
// //   }

// //   return (
// //     <div className="editor-container">
// //       <div className="editor-header">
// //         <select
// //           value={language}
// //           onChange={(e) => {
// //             const newLang = e.target.value;
// //             setLanguage(newLang);

// //             if (newLang === "java" && starterCode) {
// //               setCode(starterCode);
// //             } else {
// //               setCode("");
// //             }
// //           }}
// //         >
// //           <option value="java">Java</option>
// //           <option value="javascript">JavaScript</option>
// //         </select>

// //         <div className="editor-actions">
// //           <button onClick={handleRun} disabled={loading}>
// //             {loading ? "Running..." : "Run"}
// //           </button>
// //           <button onClick={handleSubmit} disabled={loading}>
// //             {loading ? "Submitting..." : "Submit"}
// //           </button>
// //         </div>
// //       </div>

// //       <Editor
// //         key={problemId}
// //         height="55vh"
// //         language={language}
// //         defaultValue={starterCode}
// //         theme="vs-dark"
// //         onChange={(v) => setCode(v)}
// //         options={{
// //           fontSize: 14,
// //           minimap: { enabled: false },
// //           automaticLayout: true,
// //         }}
// //       />


// //       {/* RESULT UI stays same */}
// //     </div>
// //   );
// // };

// // export default CodeEditor;





// import Editor from "@monaco-editor/react";
// import { useEffect, useState } from "react";
// import "./CodeEditor.css";
// import { runCode, submitCode, getSubmissions } from "../services/judgeService";

// const CodeEditor = ({ problemId, starterCode }) => {

//   const [language, setLanguage] = useState("java");
//   const [code, setCode] = useState("");
//   const [result, setResult] = useState(null);
//   const [submissions, setSubmissions] = useState([]);
//   const [loading, setLoading] = useState(false);

//   // ✅ Load starter code when problem changes
//   useEffect(() => {
//     if (starterCode) {
//       setCode(starterCode);
//     }
//   }, [starterCode]);

//   // ✅ Load submission history
//   useEffect(() => {
//     if (problemId) {
//       loadSubmissionHistory();
//     }
//   }, [problemId]);

//   async function loadSubmissionHistory() {
//     try {
//       const list = await getSubmissions(problemId);
//       setSubmissions(list);
//     } catch (err) {
//       console.error("Failed to load submissions:", err);
//     }
//   }

//   // ✅ RUN
//   async function handleRun() {
//     setLoading(true);
//     setResult(null);

//     try {
//       const data = await runCode(problemId, language, code);
//       setResult(data);
//     } catch (err) {
//       setResult({ result: "SERVER_ERROR", message: err.message });
//     }

//     setLoading(false);
//   }

//   // ✅ SUBMIT
//   async function handleSubmit() {
//     setLoading(true);
//     setResult(null);

//     try {
//       const data = await submitCode(problemId, language, code);
//       setResult(data);
//       await loadSubmissionHistory();
//     } catch (err) {
//       setResult({ result: "SERVER_ERROR", message: err.message });
//     }

//     setLoading(false);
//   }

//   return (
//     <div className="editor-container">
      
//       {/* HEADER */}
//       <div className="editor-header">
//         <select
//           value={language}
//           onChange={(e) => setLanguage(e.target.value)}
//         >
//           <option value="java">Java</option>
//         </select>

//         <div className="editor-actions">
//           <button className="run-btn" onClick={handleRun} disabled={loading}>
//             {loading ? "Running..." : "Run"}
//           </button>

//           <button className="submit-btn" onClick={handleSubmit} disabled={loading}>
//             {loading ? "Submitting..." : "Submit"}
//           </button>
//         </div>
//       </div>

//       {/* EDITOR */}
//       <Editor
//         height="55vh"
//         language="java"
//         value={code}
//         theme="vs-dark"
//         onChange={(v) => setCode(v || "")}
//         options={{
//           fontSize: 14,
//           minimap: { enabled: false },
//           automaticLayout: true,
//           scrollBeyondLastLine: false,
//         }}
//       />

//       {/* RESULT PANEL */}
//       {result && (
//         <div style={{
//           marginTop: "12px",
//           background: "#111",
//           padding: "12px",
//           borderRadius: "8px",

//           // maxHeight: "300px",  /* 🔥 controls height */
//           // overflowY: auto  /* 🔥 enables vertical scroll */

//         }}>
//           <h3 style={{ margin: 0 }}>
//             Result: {result.result}
//           </h3>

//           {result.failedTestcaseIndex !== null && result.failedTestcaseIndex !== undefined && (
//             <p>Failed testcase: {result.failedTestcaseIndex}</p>
//           )}

//           {result.input && (
//             <>
//               <h4>Input</h4>
//               <pre>{result.input}</pre>
//             </>
//           )}

//           {result.expectedOutput && (
//             <>
//               <h4>Expected</h4>
//               <pre>{result.expectedOutput}</pre>
//             </>
//           )}

//           {result.actualOutput && (
//             <>
//               <h4>Actual</h4>
//               <pre>{result.actualOutput}</pre>
//             </>
//           )}

//           {result.message && (
//             <>
//               <h4>Message</h4>
//               <pre>{result.message}</pre>
//             </>
//           )}
//         </div>
//       )}

//       {/* SUBMISSION HISTORY */}
//       <div style={{ marginTop: "15px" }}>
//         <h3>Submission History</h3>

//         {submissions.length === 0 ? (
//           <p>No submissions yet.</p>
//         ) : (
//           <table style={{ width: "100%", borderCollapse: "collapse" }}>
//             <thead>
//               <tr>
//                 <th style={{ textAlign: "left" }}>ID</th>
//                 <th style={{ textAlign: "left" }}>Verdict</th>
//                 <th style={{ textAlign: "left" }}>Language</th>
//                 <th style={{ textAlign: "left" }}>Time</th>
//               </tr>
//             </thead>
//             <tbody>
//               {submissions.map((s) => (
//                 <tr key={s.id}>
//                   <td>{s.id}</td>
//                   <td>{s.verdict}</td>
//                   <td>{s.language}</td>
//                   <td>{s.createdAt}</td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         )}
//       </div>

//     </div>
//   );
// };

// export default CodeEditor;





































import Editor from "@monaco-editor/react";
import { useEffect, useState } from "react";
import "./CodeEditor.css";
import { runCode, submitCode, getSubmissions } from "../services/judgeService";

const CodeEditor = ({ problemId, starterCode }) => {
  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState("");
  const [result, setResult] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(false);

  // ✅ Load starter code
  useEffect(() => {
    if (starterCode) {
      setCode(starterCode);
    }
  }, [starterCode]);

  // ✅ Load submissions
  useEffect(() => {
    if (problemId) {
      loadSubmissionHistory();
    }
  }, [problemId]);

  async function loadSubmissionHistory() {
    try {
      const list = await getSubmissions(problemId);
      setSubmissions(list);
    } catch (err) {
      console.error("Failed to load submissions:", err);
    }
  }

  // ✅ RUN
  async function handleRun() {
    setLoading(true);
    setResult(null);

    try {
      const data = await runCode(problemId, language, code);
      setResult(data);
    } catch (err) {
      setResult({ result: "SERVER_ERROR", message: err.message });
    }

    setLoading(false);
  }

  // ✅ SUBMIT
  async function handleSubmit() {
    setLoading(true);
    setResult(null);

    try {
      const data = await submitCode(problemId, language, code);
      setResult(data);
      await loadSubmissionHistory();
    } catch (err) {
      setResult({ result: "SERVER_ERROR", message: err.message });
    }

    setLoading(false);
  }

  return (
    <div className="editor-container">

      {/* HEADER */}
      <div className="editor-header">
        <select
          value={language}
          onChange={(e) => setLanguage(e.target.value)}
        >
          <option value="java">Java</option>
        </select>

        <div className="editor-actions">
          <button className="run-btn" onClick={handleRun} disabled={loading}>
            {loading ? "Running..." : "Run"}
          </button>

          <button className="submit-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Submitting..." : "Submit"}
          </button>
        </div>
      </div>

      {/* EDITOR */}
      <Editor
        height="55vh"
        language="java"
        value={code}
        theme="vs-dark"
        onChange={(v) => setCode(v || "")}
        options={{
          fontSize: 14,
          minimap: { enabled: false },
          automaticLayout: true,
          scrollBeyondLastLine: false,
        }}
      />

      {/* RESULT PANEL 🔥 FIXED */}
      {result && (
        <div className="result-box">
          <h3>Result: {result.result}</h3>

          {result.failedTestcaseIndex !== null &&
            result.failedTestcaseIndex !== undefined && (
              <p>Failed testcase: {result.failedTestcaseIndex}</p>
            )}

          {result.input && (
            <>
              <h4>Input</h4>
              <pre>{result.input}</pre>
            </>
          )}

          {result.expectedOutput && (
            <>
              <h4>Expected</h4>
              <pre>{result.expectedOutput}</pre>
            </>
          )}

          {result.actualOutput && (
            <>
              <h4>Actual</h4>
              <pre>{result.actualOutput}</pre>
            </>
          )}

          {result.message && (
            <>
              <h4>Message</h4>
              <pre>{result.message}</pre>
            </>
          )}
        </div>
      )}

      {/* SUBMISSION HISTORY */}
      <div className="submission-box">
        <h3>Submission History</h3>

        {submissions.length === 0 ? (
          <p>No submissions yet.</p>
        ) : (
          <table className="submission-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Verdict</th>
                <th>Language</th>
                <th>Time</th>
              </tr>
            </thead>
            <tbody>
              {submissions.map((s) => (
                <tr key={s.id}>
                  <td>{s.id}</td>
                  <td>{s.verdict}</td>
                  <td>{s.language}</td>
                  <td>{s.createdAt}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

    </div>
  );
};

export default CodeEditor;