import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./VerifyOtp.css";

function VerifyOtp() {

const [otp, setOtp] = useState("");
const navigate = useNavigate();

const email = localStorage.getItem("resetEmail");

const verifyOtp = async () => {


try {

  const response = await fetch(
    `http://localhost:8080/auth/verify-otp?email=${email}&otp=${otp}`,
    { method: "POST" }
  );

  if (!response.ok) {
    alert("Invalid OTP");
    return;
  }

  alert("OTP verified");

  navigate("/reset-password");

} catch (error) {
  alert("Error verifying OTP");
}


};

return ( <div className="otp-container">


  <h2>Enter OTP</h2>

  <input
    type="text"
    className="otp-input"
    style={{ maxWidth: "300px" }}
    placeholder="Enter OTP"
    value={otp}
    onChange={(e) => setOtp(e.target.value)}
  />

  <button
    className="otp-button"
    onClick={verifyOtp}
  >
    Verify OTP
  </button>

</div>


);
}

export default VerifyOtp;
