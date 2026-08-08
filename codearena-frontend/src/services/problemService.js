// const BASE_URL = "http://localhost:8080/problems";

// export const getAllProblems = async () => {
//   const response = await fetch(BASE_URL);

//   if (!response.ok) {
//     throw new Error("Failed to fetch problems");
//   }

//   return response.json();
// };

// export const getProblemBySlug = async (slug) => {
//   const response = await fetch(`${BASE_URL}/problem/${slug}`);

//   if (!response.ok) {
//     throw new Error("Problem not found");
//   }

//   return response.json();
// };










import apiFetch from "./apiClient";

export const getAllProblems = async () => apiFetch("/problems");

export const getProblemBySlug = async (slug) => apiFetch(`/problems/problem/${slug}`);

// Admin only — includes hidden/contest-exclusive problems, for general admin tooling.
export const getAllProblemsForAdmin = async () => apiFetch("/problems/admin/all");

// Admin only — ONLY hidden/contest-exclusive problems. This is what the
// contest builder's picker should call, not getAllProblemsForAdmin — an
// already-public problem shouldn't be offered as contest content again.
export const getHiddenProblemsForAdmin = async () => apiFetch("/problems/admin/hidden");