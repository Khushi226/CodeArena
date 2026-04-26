const BASE_URL = "http://localhost:8080";

async function safeJson(res) {
  const text = await res.text(); // read raw response

  // If backend returned nothing
  if (!text || text.trim() === "") {
    throw new Error(`Empty response from server (status ${res.status})`);
  }

  // Try parsing JSON
  try {
    return JSON.parse(text);
  } catch (e) {
    console.log("Backend returned non-JSON response:", text);
    throw new Error(`Server returned invalid JSON (status ${res.status})`);
  }
}

export async function runCode(problemId, language, code) {
  const res = await fetch(`${BASE_URL}/run`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ problemId, language, code }),
  });

  return await safeJson(res);
}

// export async function submitCode(problemId, language, code) {
//   const res = await fetch(`${BASE_URL}/submit`, {
//     method: "POST",
//     headers: { "Content-Type": "application/json" },
//     body: JSON.stringify({ problemId, language, code }),
//   });
//   return await safeJson(res);
// }

    export async function submitCode(problemId, language, code) {
      const token = localStorage.getItem("token");

      const res = await fetch(`${BASE_URL}/submit`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ problemId, language, code }),
      });

      // ✅ IMPORTANT: read as text first
      const text = await res.text();

      console.log("SUBMIT STATUS:", res.status);
      console.log("SUBMIT RESPONSE TEXT:", text);

      // if backend returns empty or HTML, JSON.parse will fail
      let data;
      try {
        data = JSON.parse(text);
      } catch (e) {
        throw new Error("Backend returned invalid JSON: " + text);
      }

      return data;
    }


// export async function getSubmissions(problemId) {
//   const res = await fetch(`${BASE_URL}/submissions/user/1/problem/${problemId}`);
//   return await safeJson(res);
// }
export async function getSubmissions(problemId) {
  const token = localStorage.getItem("token");

  const res = await fetch(
    `${BASE_URL}/submissions/user/1/problem/${problemId}`,
    {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    }
  );

  const text = await res.text();

  if (!res.ok) {
    throw new Error(text || `Request failed with status ${res.status}`);
  }

  return JSON.parse(text);
}
