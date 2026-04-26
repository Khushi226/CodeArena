// // import { useState, useEffect } from "react";
// // import "./Settings.css";

// // function Settings() {

// //   const [user, setUser] = useState(null);
// //   const [theme, setTheme] = useState("light");

// //   const [showModal, setShowModal] = useState(false);
// //   const [field, setField] = useState("");
// //   const [value, setValue] = useState("");

// //   useEffect(() => {

// //     const token = localStorage.getItem("token");

// //     fetch("http://localhost:8080/me", {
// //       headers: {
// //         Authorization: "Bearer " + token
// //       }
// //     })
// //       .then(res => res.json())
// //       .then(data => setUser(data));

// //   }, []);

// //   const toggleTheme = () => {
// //     const newTheme = theme === "light" ? "dark" : "light";
// //     setTheme(newTheme);
// //     document.body.setAttribute("data-theme", newTheme);
// //   };

// //   const openModal = (fieldName) => {
// //     setField(fieldName);
// //     setValue("");
// //     setShowModal(true);
// //   };

// //   const handleSave = () => {

// //     const token = localStorage.getItem("token");

// //     fetch(`http://localhost:8080/users/me/${field}`, {
// //       method: "PUT",
// //       headers: {
// //         "Content-Type": "application/json",
// //         Authorization: "Bearer " + token
// //       },
// //       body: JSON.stringify({ value })
// //     })
// //       .then(res => res.json())
// //       .then(data => {
// //         setUser(data);
// //         setShowModal(false);
// //       });
// //   };

// //   if (!user) return <p>Loading...</p>;

// //   return (
// //     <div className="settings-container">

// //       <h2 className="settings-title">General</h2>

// //       <div className="settings-card">

// //         <div className="settings-row">
// //           <div>
// //             <h4>LeetCode ID</h4>
// //             <p>{user.username}</p>
// //           </div>
// //           <button onClick={() => openModal("username")}>Edit</button>
// //         </div>

// //         <div className="settings-row">
// //           <div>
// //             <h4>Email</h4>
// //             <p>{user.email}</p>
// //           </div>
// //           <button onClick={() => openModal("email")}>Edit</button>
// //         </div>

// //         <div className="settings-row">
// //           <div>
// //             <h4>Password</h4>
// //             <p>********</p>
// //           </div>
// //           <button onClick={() => openModal("password")}>Change</button>
// //         </div>

// //       </div>

// //       <h2 className="settings-title">Appearance</h2>

// //       <div className="settings-card">

// //         <div className="settings-row">
// //           <div>
// //             <h4>Theme</h4>
// //             <p>{theme === "light" ? "Light Mode" : "Dark Mode"}</p>
// //           </div>

// //           <button onClick={toggleTheme}>
// //             Switch Theme
// //           </button>
// //         </div>

// //       </div>

// //       {showModal && (
// //         <div className="modal">

// //           <div className="modal-box">

// //             <h3>Edit {field}</h3>

// //             <input
// //               type="text"
// //               placeholder={`Enter new ${field}`}
// //               value={value}
// //               onChange={(e) => setValue(e.target.value)}
// //             />

// //             <div className="modal-buttons">
// //               <button onClick={handleSave}>Save</button>
// //               <button onClick={() => setShowModal(false)}>Cancel</button>
// //             </div>

// //           </div>

// //         </div>
// //       )}

// //     </div>
// //   );
// // }

// // export default Settings;












// import { useState, useEffect } from "react";
// import "./Settings.css";

// function Settings() {

//   const [user, setUser] = useState(null);

//   // const [theme, setTheme] = useState(
//   //   localStorage.getItem("theme") || "dark"
//   // );

//   const [showModal, setShowModal] = useState(false);
//   const [field, setField] = useState("");
//   const [value, setValue] = useState("");



//   /* =========================
//      APPLY THEME ON PAGE LOAD
//      ========================= */
//   useEffect(() => {
//     document.body.setAttribute("data-theme", theme);
//   }, []);



//   /* =========================
//      FETCH USER DATA
//      ========================= */
//   useEffect(() => {

//     const token = localStorage.getItem("token");

//     fetch("http://localhost:8080/me", {
//       headers: {
//         Authorization: "Bearer " + token
//       }
//     })
//       .then(res => res.json())
//       .then(data => setUser(data));

//   }, []);



//   /* =========================
//      THEME TOGGLE
//      ========================= */
//   const toggleTheme = () => {

//     const newTheme = theme === "light" ? "dark" : "light";

//     setTheme(newTheme);

//     document.body.setAttribute("data-theme", newTheme);

//     localStorage.setItem("theme", newTheme);
//   };



//   const openModal = (fieldName) => {
//     setField(fieldName);
//     setValue("");
//     setShowModal(true);
//   };



//   const handleSave = () => {

//     const token = localStorage.getItem("token");

//     fetch(`http://localhost:8080/users/me/${field}`, {
//       method: "PUT",
//       headers: {
//         "Content-Type": "application/json",
//         Authorization: "Bearer " + token
//       },
//       body: JSON.stringify({ value })
//     })
//       .then(res => res.json())
//       .then(data => {
//         setUser(data);
//         setShowModal(false);
//       });
//   };



//   if (!user) return <p>Loading...</p>;



//   return (
//     <div className="settings-container">

//       <h2 className="settings-title">General</h2>

//       <div className="settings-card">

//         <div className="settings-row">
//           <div>
//             <h4>LeetCode ID</h4>
//             <p>{user.username}</p>
//           </div>
//           <button onClick={() => openModal("username")}>Edit</button>
//         </div>

//         <div className="settings-row">
//           <div>
//             <h4>Email</h4>
//             <p>{user.email}</p>
//           </div>
//           <button onClick={() => openModal("email")}>Edit</button>
//         </div>

//         <div className="settings-row">
//           <div>
//             <h4>Password</h4>
//             <p>********</p>
//           </div>
//           <button onClick={() => openModal("password")}>Change</button>
//         </div>

//       </div>



//       <h2 className="settings-title">Appearance</h2>

//       <div className="settings-card">

//         <div className="settings-row">
//           <div>
//             <h4>Theme</h4>
//             <p>Dark Mode</p>
//           </div>

//           <button onClick={toggleTheme}>
//             Switch Theme
//           </button>

//         </div>

//       </div>



//       {showModal && (
//         <div className="modal">

//           <div className="modal-box">

//             <h3>Edit {field}</h3>

//             <input
//               type="text"
//               placeholder={`Enter new ${field}`}
//               value={value}
//               onChange={(e) => setValue(e.target.value)}
//             />

//             <div className="modal-buttons">
//               <button onClick={handleSave}>Save</button>
//               <button onClick={() => setShowModal(false)}>Cancel</button>
//             </div>

//           </div>

//         </div>
//       )}

//     </div>
//   );
// }

// export default Settings;













import { useState, useEffect } from "react";
import "./Settings.css";

function Settings() {

  const [user, setUser] = useState(null);

  const [showModal, setShowModal] = useState(false);
  const [field, setField] = useState("");
  const [value, setValue] = useState("");

  /* =========================
     FORCE DARK THEME ALWAYS
     ========================= */
  useEffect(() => {
    document.body.setAttribute("data-theme", "dark");
  }, []);

  /* =========================
     FETCH USER DATA
     ========================= */
  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/me", {
      headers: {
        Authorization: "Bearer " + token
      }
    })
      .then(res => res.json())
      .then(data => setUser(data));
  }, []);

  const openModal = (fieldName) => {
    setField(fieldName);
    setValue("");
    setShowModal(true);
  };

  const handleSave = () => {
    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/users/me/${field}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token
      },
      body: JSON.stringify({ value })
    })
      .then(res => res.json())
      .then(data => {
        setUser(data);
        setShowModal(false);
      });
  };

  if (!user) return <p>Loading...</p>;

  return (
    <div className="settings-container">

      <h2 className="settings-title">General</h2>

      <div className="settings-card">

        <div className="settings-row">
          <div>
            <h4>LeetCode ID</h4>
            <p>{user.username}</p>
          </div>
          <button onClick={() => openModal("username")}>Edit</button>
        </div>

        <div className="settings-row">
          <div>
            <h4>Email</h4>
            <p>{user.email}</p>
          </div>
          <button onClick={() => openModal("email")}>Edit</button>
        </div>

        <div className="settings-row">
          <div>
            <h4>Password</h4>
            <p>********</p>
          </div>
          <button onClick={() => openModal("password")}>Change</button>
        </div>

      </div>

      <h2 className="settings-title">Appearance</h2>

      <div className="settings-card">

        <div className="settings-row">
          <div>
            <h4>Theme</h4>
            <p>Dark Mode</p>
          </div>

          {/* Button removed since no switching */}
        </div>

      </div>

      {showModal && (
        <div className="modal">

          <div className="modal-box">

            <h3>Edit {field}</h3>

            <input
              type="text"
              placeholder={`Enter new ${field}`}
              value={value}
              onChange={(e) => setValue(e.target.value)}
            />

            <div className="modal-buttons">
              <button onClick={handleSave}>Save</button>
              <button onClick={() => setShowModal(false)}>Cancel</button>
            </div>

          </div>

        </div>
      )}

    </div>
  );
}

export default Settings;