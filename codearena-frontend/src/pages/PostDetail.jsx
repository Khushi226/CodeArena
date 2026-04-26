import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./PostDetail.css";

function PostDetail() {

    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [comments, setComments] = useState([]);
    const [comment, setComment] = useState("");

    useEffect(() => {

        fetch(`http://localhost:8080/discuss/${id}`)
            .then(res => res.json())
            .then(data => setPost(data));

        fetch(`http://localhost:8080/discuss/${id}/comments`)
            .then(res => res.json())
            .then(data => {
                console.log("comments:", data);
                setComments(data);
            });

    }, [id]);

    function timeAgo(date) {

        const seconds = Math.floor((new Date() - new Date(date)) / 1000)

        let interval = seconds / 31536000
        if (interval > 1) return Math.floor(interval) + " years ago"

        interval = seconds / 2592000
        if (interval > 1) return Math.floor(interval) + " months ago"

        interval = seconds / 86400
        if (interval > 1) return Math.floor(interval) + " days ago"

        interval = seconds / 3600
        if (interval > 1) return Math.floor(interval) + " hours ago"

        interval = seconds / 60
        if (interval > 1) return Math.floor(interval) + " minutes ago"

        return "just now"
    }

    const submitComment = async () => {

        if (comment.trim().length < 10) {
            alert("Comment must be at least 10 characters")
            return
        }

        await fetch(`http://localhost:8080/discuss/${id}/comment`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({
                userId: localStorage.getItem("userId"),
                content: comment
            })
        })

        setComment("")

        // fetch comments again
        const res = await fetch(`http://localhost:8080/discuss/${id}/comments`)
        const data = await res.json()

        setComments(data)

    }


    if (!post) return <p>Loading...</p>;

    return (

        <div className="post-detail-container">

            <div className="post-card">

                <h2 className="post-title">{post.title}</h2>

                <p className="post-content">{post.content}</p>

                <div className="post-actions">


                    <span className="comment-count">
                        💬 {comments.length} Comments
                    </span>

                </div>

                <h3 className="comments-title">Comments</h3>

                <div className="comments-list">

                    {comments.length === 0 && (
                        <p className="no-comments">No comments yet</p>
                    )}

                    {comments.map((c) => (
                        <div className="comment-card">

                            <div className="comment-header">
                                <strong>{c.username}</strong>
                                <span className="comment-time">
                                    {timeAgo(c.createdAt)}
                                </span>
                            </div>

                            <p>{c.content}</p>

                        </div>
                    ))}

                </div>

                <div className="comment-input-section">

                    <textarea
                        className="comment-input"
                        placeholder="Write your comment..."
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                    />

                    <button className="comment-btn" onClick={submitComment}>
                        Add Comment
                    </button>

                </div>

            </div>

        </div>

    );
}

export default PostDetail;