const BASE_URL = "http://localhost:8080/problems";

export const getAllProblems = async () => {
  const response = await fetch(BASE_URL);

  if (!response.ok) {
    throw new Error("Failed to fetch problems");
  }

  return response.json();
};

export const getProblemBySlug = async (slug) => {
  const response = await fetch(`${BASE_URL}/problem/${slug}`);

  if (!response.ok) {
    throw new Error("Problem not found");
  }

  return response.json();
};
