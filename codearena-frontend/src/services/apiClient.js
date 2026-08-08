const BASE_URL = "http://localhost:8080";

async function apiFetch(path, options = {}) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    },
  });

  // Session died — clear storage and tell the rest of the app
  if (res.status === 401) {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("userId");
    window.dispatchEvent(new Event("auth-expired"));
    throw new Error("Session expired. Please log in again.");
  }

  const text = await res.text();

  if (!res.ok) {
    throw new Error(text || `Request failed with status ${res.status}`);
  }

  if (!text || text.trim() === "") return null;

  try {
    return JSON.parse(text);
  } catch {
    throw new Error(`Server returned invalid JSON: ${text}`);
  }
}

export default apiFetch;