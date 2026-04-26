
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { oneDark } from "react-syntax-highlighter/dist/cjs/styles/prism";
import './LearnProblem.css';

function LearnProblem() {

  const { slug } = useParams();

  const [article, setArticle] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {

    const fetchArticle = async () => {
      try {

        const res = await fetch(`http://localhost:8080/api/learn/${slug}`);

        if (!res.ok) {
          throw new Error("Article not found");
        }

        const data = await res.text();

        setArticle(data);

      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchArticle();

  }, [slug]);

  if (loading) return <div>Loading article...</div>;

  if (error) return <div>{error}</div>;

  return (
    <div className="learn-article-container">

      <div className="learn-article">
        {/* <pre>{article}</pre> */}
        <ReactMarkdown>
        {article}
      </ReactMarkdown>
        {/* <ReactMarkdown
          className="learn-article"
          components={{
            code({ node, inline, className, children, ...props }) {
              const match = /language-(\w+)/.exec(className || "");

              return !inline && match ? (
                <SyntaxHighlighter
                  style={oneDark}
                  language={match[1]}
                  PreTag="div"
                  {...props}
                >
                  {String(children).replace(/\n$/, "")}
                </SyntaxHighlighter>
              ) : (
                <code className={className} {...props}>
                  {children}
                </code>
              );
            }
          }}
        >
          {article || " "}
        </ReactMarkdown> */}
      </div>

    </div>
  );
}

export default LearnProblem;