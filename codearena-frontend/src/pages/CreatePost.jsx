import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./CreatePost.css";

function CreatePost() {

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async () => {

        const token = localStorage.getItem("token");
        const userId = localStorage.getItem("userId");

        try {

            const res = await fetch("http://localhost:8080/discuss", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    userId: Number(userId),
                    title: title,
                    content: content
                })
            });

            if (!res.ok) {
                console.error("Post failed:", res.status);
                return;
            }

            navigate("/discuss");

        } catch (err) {
            console.error("Error creating post:", err);
        }
    };

    return (

        <div className="create-post-container">

            <h1>Create Discussion</h1>

            <input
                type="text"
                placeholder="Enter title..."
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <textarea
                placeholder="Write your post..."
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />

            <button onClick={handleSubmit}>
                Post
            </button>

        </div>

    );
}

export default CreatePost;