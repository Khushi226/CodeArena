import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function Signup() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };



    const handleSubmit = async (e) => {
    e.preventDefault(); // stops page refresh

    try {
        const response = await fetch("http://localhost:8080/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
        });

        const data = await response.json();

        alert(data.message);

        // optional redirect
        window.location.href = "/login";

    } catch (error) {
        console.error(error);
        alert("Signup failed");
    }
    };



  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div >
        <div className="card shadow p-4 rounded-4">
          <h3 className="text-center mb-3">Create Account</h3>

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Username</label>
              <input
                type="text"
                name="username"
                className="form-control"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="email"
                name="email"
                className="form-control"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Password</label>
              <input
                type="password"
                name="password"
                className="form-control"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>

            <button className="btn btn-dark w-100 mt-2">
              Sign Up
            </button>
          </form>

          <p className="text-center mt-3 text-muted">
            Already have an account? <a href="/login">Login</a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Signup;






