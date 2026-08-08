

// import Editor from "@monaco-editor/react";
// import { useEffect, useState } from "react";
// import {useNavigate} from "react-router-dom";
// import "./CodeEditor.css";
// import { runCode, submitCode, getSubmissions } from "../services/judgeService";

// const CodeEditor = ({ problemId, starterCode }) => {
//   const [language, setLanguage] = useState("java");
//   const [code, setCode] = useState("");
//   const [result, setResult] = useState(null);
//   const [submissions, setSubmissions] = useState([]);
//   const [loading, setLoading] = useState(false);

//   const navigate =useNavigate();

//   // ✅ Load starter code
//   useEffect(() => {
//     if (starterCode) {
//       setCode(starterCode);
//     }
//   }, [starterCode]);

//   // ✅ Load submissions
//   useEffect(() => {
//     if (problemId) {
//       loadSubmissionHistory();
//     }
//   }, [problemId]);

//   async function loadSubmissionHistory() {
//     try {
//       const list = await getSubmissions(problemId);
//       setSubmissions(list);
//     } 
//     catch (err) {
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
//     } 
//     // catch (err) {
//     //   setResult({ result: "SERVER_ERROR", message: err.message });
//     // }
//     catch (err) {
//       if (err.message.includes("Session expired")) {
//         alert("Your session expired. Please log in again.");
//         navigate("/login");
//       } else {
//         setResult({ result: "SERVER_ERROR", message: err.message });
//       }
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
//     } 
//     // catch (err) {
//     //   setResult({ result: "SERVER_ERROR", message: err.message });
//     // }
//     catch (err) {
//       if (err.message.includes("Session expired")) {
//         alert("Your session expired. Please log in again.");
//         navigate("/login");
//       } else {
//         setResult({ result: "SERVER_ERROR", message: err.message });
//       }
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

//       {/* RESULT PANEL 🔥 FIXED */}
//       {result && (
//         <div className="result-box">
//           <h3>Result: {result.result}</h3>

//           {result.failedTestcaseIndex !== null &&
//             result.failedTestcaseIndex !== undefined && (
//               <p>Failed testcase: {result.failedTestcaseIndex}</p>
//             )}

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
//       <div className="submission-box">
//         <h3>Submission History</h3>

//         {submissions.length === 0 ? (
//           <p>No submissions yet.</p>
//         ) : (
//           <table className="submission-table">
//             <thead>
//               <tr>
//                 <th>ID</th>
//                 <th>Verdict</th>
//                 <th>Language</th>
//                 <th>Time</th>
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
import { useEffect, useState, useRef } from "react";
import "./CodeEditor.css";
import { runCode, submitCode, getSubmissions } from "../services/judgeService";

const CodeEditor = ({ problemId, starterCode, submitFn, loadSubmissionsFn, storageKey }) => {
  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState("");
  const [result, setResult] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(false);

  const draftTimeoutRef = useRef(null);

  
  // ✅ Load starter code
  useEffect(() => {
    const key = `codearena_draft_${storageKey ?? problemId}`;
    const savedDraft = problemId ? localStorage.getItem(key) : null;

    if (savedDraft !== null) {
      setCode(savedDraft);
    } else if (starterCode) {
      setCode(starterCode);
    }
  }, [starterCode, problemId, storageKey]);

  // ✅ Load submissions
  useEffect(() => {
    if (problemId) {
      loadSubmissionHistory();
    }
  }, [problemId]);

  useEffect(() => {
    return () => {
      if (draftTimeoutRef.current) clearTimeout(draftTimeoutRef.current);
    };
  }, []);

  async function loadSubmissionHistory() {
    try {
      const list = loadSubmissionsFn ? await loadSubmissionsFn(problemId) : await getSubmissions(problemId);
      setSubmissions(list);

      const key = `codearena_draft_${storageKey ?? problemId}`;
      const hasDraft = localStorage.getItem(key) !== null;

      if (!hasDraft && list && list.length > 0 && list[0].code) {
        setCode(list[0].code);
      }
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
      const data = submitFn ? await submitFn(problemId, language, code) : await submitCode(problemId, language, code);
      setResult(data);
      await loadSubmissionHistory();
    } catch (err) {
      setResult({ result: "SERVER_ERROR", message: err.message });
    }

    setLoading(false);
  }

  function handleCodeChange(value) {
    const newCode = value || "";
    setCode(newCode);

    const key = `codearena_draft_${storageKey ?? problemId}`;
    if (draftTimeoutRef.current) clearTimeout(draftTimeoutRef.current);
    draftTimeoutRef.current = setTimeout(() => {
      localStorage.setItem(key, newCode);
    }, 500);
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
        onChange={handleCodeChange}
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