import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Discuss.css";

function Discuss() {

  const [posts, setPosts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/discuss", {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
      .then(res => {
        if (!res.ok) {
          console.log("Request failed:", res.status);
          return;
        }
        return res.json();
      })
      .then(data => {
        if (data) setPosts(data);
      })
      .catch(err => console.error(err));

  }, []);
  return (
    <div className="discuss-container">

      <div className="discuss-header">

        <h1>Discuss</h1>

        <button
          className="create-post-btn"
          onClick={() => navigate("/discuss/create")}
        >
          Create Post
        </button>

      </div>

      <div className="post-list">

        {posts.length === 0 ? (

          <p>No discussions yet.</p>

        ) : (

          posts.map((post) => (

            <div
              key={post.id}
              className="post-card"
              onClick={() => navigate(`/discuss/${post.id}`)}
            >

              <h3>{post.title}</h3>

              <p className="post-preview">
                {post.content.slice(0, 150)}...
              </p>

              <div className="post-meta">

                <span>Post ID: {post.id}</span>

                <span>
                  {new Date(post.createdAt).toLocaleString()}
                </span>

              </div>

            </div>

          ))

        )}

      </div>

    </div>
  );
}

export default Discuss;