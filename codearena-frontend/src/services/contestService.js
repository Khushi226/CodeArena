const BASE_URL = "http://localhost:8080/contests";

function authHeaders() {
  const token = localStorage.getItem("token");
  return token ? { Authorization: `Bearer ${token}` } : {};
}

async function safeJson(res) {
  const text = await res.text();

  if (!res.ok) {
    // Backend sends plain-text reasons via ResponseStatusException
    // (e.g. "Contest hasn't started yet") — surface that directly.
    throw new Error(text || `Request failed with status ${res.status}`);
  }

  if (!text || text.trim() === "") return null;

  try {
    return JSON.parse(text);
  } catch {
    throw new Error("Server returned invalid JSON: " + text);
  }
}

export async function getAllContests() {
  const res = await fetch(BASE_URL, { headers: authHeaders() });
  return safeJson(res);
}

// Also returns { serverTimeMs } computed from the response's Date header,
// so the arena's countdown timer isn't trusting the user's own clock.
export async function getContestBySlug(slug) {
  const res = await fetch(`${BASE_URL}/${slug}`, { headers: authHeaders() });
  const contest = await safeJson(res);
  const serverDateHeader = res.headers.get("date");
  const serverTimeMs = serverDateHeader ? new Date(serverDateHeader).getTime() : Date.now();
  return { contest, serverTimeMs };
}

export async function registerForContest(contestId) {
  const res = await fetch(`${BASE_URL}/${contestId}/register`, {
    method: "POST",
    headers: authHeaders(),
  });
  return safeJson(res);
}

export async function getContestProblem(contestId, problemId) {
  const res = await fetch(`${BASE_URL}/${contestId}/problems/${problemId}`, {
    headers: authHeaders(),
  });
  return safeJson(res);
}

export async function getContestSubmissions(contestId, problemId) {
  const res = await fetch(`${BASE_URL}/${contestId}/problems/${problemId}/submissions`, {
    headers: authHeaders(),
  });
  return safeJson(res);
}

export async function submitContestSolution(contestId, problemId, language, code) {
  const res = await fetch(`${BASE_URL}/${contestId}/submit`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...authHeaders(),
    },
    body: JSON.stringify({ problemId, language, code }),
  });
  return safeJson(res);
}

export async function getLeaderboard(contestId) {
  const res = await fetch(`${BASE_URL}/${contestId}/leaderboard`, {
    headers: authHeaders(),
  });
  return safeJson(res);
}

export async function createContest(payload) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...authHeaders(),
    },
    body: JSON.stringify(payload),
  });
  return safeJson(res);
}