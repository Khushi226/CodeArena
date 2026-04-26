import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";

function Login({ setIsLoggedIn }) {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: ""
  });

  const [email, setEmail] = useState("");
  const [showForgot, setShowForgot] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {

      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error();
      }

      const data = await response.json();

      localStorage.setItem("token", data.token);
      localStorage.setItem("username", data.username);
      localStorage.setItem("userId", data.userId);

      // update global login state
      setIsLoggedIn(true);

      alert("Login successful!");

      navigate("/dashboard");

    } catch (error) {
      alert("Invalid username or password");
    }
  };

  const handleForgotPassword = async () => {

    if (!email) {
      alert("Please enter email");
      return;
    }

    try {

      const response = await fetch(
        "http://localhost:8080/auth/forgot-password?email=" + email,
        { method: "POST" }
      );

      const text = await response.text();

      alert(text);

      localStorage.setItem("resetEmail", email);

      navigate("/verify-otp");

    } catch (error) {
      alert("Error sending OTP");
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">

      <div className="card shadow p-4 rounded-4" style={{ width: "350px" }}>

        <h3 className="text-center mb-3">Login</h3>

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

          <div className="text-end mb-2">
            <button
              type="button"
              className="btn btn-link p-0"
              onClick={() => setShowForgot(true)}
            >
              Forgot Password?
            </button>
          </div>

          <button type="submit" className="btn btn-dark w-100 mt-2">
            Login
          </button>

        </form>

        <p className="text-center mt-3 text-muted">
          Don’t have an account? <span style={{cursor:"pointer",color:"#0d6efd"}} onClick={()=>navigate("/signup")}>Sign up</span>
        </p>

      </div>

      {/* Forgot Password Popup */}
      {showForgot && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center"
          }}
        >
          <div className="card p-4" style={{ width: "300px" }}>

            <h5 className="mb-3">Forgot Password</h5>

            <input
              type="email"
              className="form-control mb-3"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <button
              className="btn btn-dark w-100"
              onClick={handleForgotPassword}
            >
              Send OTP
            </button>

            <button
              className="btn btn-secondary mt-2"
              onClick={() => setShowForgot(false)}
            >
              Cancel
            </button>

          </div>
        </div>
      )}

    </div>
  );
}

export default Login;