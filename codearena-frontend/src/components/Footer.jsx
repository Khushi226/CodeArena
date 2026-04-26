import "./Footer.css";
import { useNavigate } from "react-router-dom";

function Footer() {

  const navigate = useNavigate();

  return (
    <footer className="footer">

      <div className="footer-container">

        {/* LEFT */}
        <div
          className="footer-logo"
          onClick={() => navigate("/")}
        >
          {"</>"} <span>CodeArena</span>
        </div>


        {/* RIGHT */}
        <div className="footer-text">
          © 2026 CodeArena. Level up your coding skills.
        </div>

      </div>

    </footer>
  );
}

export default Footer;