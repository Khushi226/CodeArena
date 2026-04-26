import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./Navbar.css";

// helper function to get initials
function getInitials(name) {
  if (!name) return "";

  const words = name.trim().split(" ");

  if (words.length === 1) {
    return words[0].charAt(0).toUpperCase();
  }

  return (words[0].charAt(0) + words[1].charAt(0)).toUpperCase();
}

function Navbar({ isLoggedIn, setIsLoggedIn }) {

  const navigate = useNavigate();
  const location = useLocation();

  const [username, setUsername] = useState(null);
  const [openMenu, setOpenMenu] = useState(false);

  // update username when route changes
  useEffect(() => {

    const storedUser = localStorage.getItem("username");
    setUsername(storedUser);

    // always close dropdown on route change
    setOpenMenu(false);

  }, [location]);

  // close dropdown when clicking outside
  useEffect(() => {

    const handleClickOutside = () => {
      setOpenMenu(false);
    };

    window.addEventListener("click", handleClickOutside);

    return () => {
      window.removeEventListener("click", handleClickOutside);
    };

  }, []);

  const handleLogout = () => {

    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("userId");

    setIsLoggedIn(false);
    setUsername(null);

    navigate("/");

  };

  return (
    <nav className="navbar">

      <div className="navbar-container">

        {/* LEFT */}
        <div className="navbar-left">
          <h2
            className="logo"
            onClick={() => navigate("/")}
          >
            CodeArena
          </h2>
        </div>

        {/* CENTER */}
        <div className="nav-center">

          <span
            className="nav-link"
            onClick={() => navigate("/problems")}
          >
            Problems
          </span>

          <span
            className="nav-link"
            onClick={() => navigate("/discuss")}
          >
            Discuss
          </span>

          <span
            className="nav-link"
            onClick={() => navigate("/learn")}
          >
            Learn
          </span>

        </div>

        {/* RIGHT */}
        <div className="nav-right">

          {username ? (

            <div className="profile-menu">

              <img
                src={`https://ui-avatars.com/api/?name=${getInitials(username)}&background=22c55e&color=fff&size=128`}
                alt="profile"
                className="profile-avatar"
                onClick={(e) => {
                  e.stopPropagation();
                  setOpenMenu(prev => !prev);
                }}
              />

              {openMenu && (

                <div
                  className="dropdown-menu"
                  onClick={(e) => e.stopPropagation()}
                >

                  <div
                    onClick={() => {
                      navigate("/dashboard");
                      setOpenMenu(false);
                    }}
                  >
                    Dashboard
                  </div>

                  <div
                    onClick={() => {
                      navigate("/submissions");
                      setOpenMenu(false);
                    }}
                  >
                    My Submissions
                  </div>

                  <div
                    onClick={() => {
                      navigate("/settings");
                      setOpenMenu(false);
                    }}
                  >
                    Settings
                  </div>

                  <div
                    className="logout-option"
                    onClick={handleLogout}
                  >
                    Logout
                  </div>

                </div>

              )}

            </div>

          ) : (

            <button
              className="login-btn"
              onClick={() => navigate("/login")}
            >
              Login
            </button>

          )}

        </div>

      </div>

    </nav>
  );
}

export default Navbar;