import { useState } from "react";
import { useNavigate } from "react-router-dom";

function ResetPassword() {

    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const email = localStorage.getItem("resetEmail");

    const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

    const resetPassword = async () => {

        try {

            const response = await fetch(
                `${API_BASE_URL}/auth/reset-password?email=${email}&newPassword=${password}`,
                { method: "POST" }
            );

            const text = await response.text();

            alert(text);

            navigate("/login");

        } catch (error) {
            alert("Error resetting password");
        }

    };

    return (
        <>
            <h3>Reset Password</h3><input
                type="password"
                className="form-control mt-3"
                placeholder="New Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)} /><button
                    className="btn btn-dark mt-3"
                    onClick={resetPassword}
                >
                Reset Password
            </button>
    </>


);
}

export default ResetPassword;