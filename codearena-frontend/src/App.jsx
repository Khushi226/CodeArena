import { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'

// import StatsCards from './components/StatsCards';
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

import Signup from './pages/signup'
import Login from './pages/Login'
import Problems from "./pages/Problems";
import ProblemDetail from "./pages/ProblemDetail";
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Settings from './pages/Settings';
import Submissions from './pages/Submissions';
import Discuss from './pages/Discuss';
import CreatePost from "./pages/CreatePost";
import PostDetail from "./pages/PostDetail";
import FeaturedProblems from "./components/FeaturedProblems";
import Footer from './components/Footer';
import VerifyOtp from "./pages/VerifyOtp";
import ResetPassword from "./pages/ResetPassword";
import LearnPage from './pages/LearnPage';
import LearnProblem from './pages/LearnProblem';
import TopicsSection from './components/TopicSection';
import ProblemsByTopic from './pages/ProblemsByTopic';

function Home() {

  const navigate = useNavigate();

  return (
    <>
      {/* HERO SECTION */}
      <section className="hero-section">
        <div className="hero-content">

          <h1 className="hero-title">
            Master the Art of <br />
            <span className="gradient-text">Problem Solving</span>
          </h1>

          <p className="hero-subtitle">
            Sharpen your coding skills with handpicked problems. Practice and grow with CodeArena.
          </p>

          <div className="hero-buttons">
            <button
              className="btn btn-success btn-lg"
              onClick={() => navigate("/problems")}
            >
              Start Solving →
            </button>

            <button
              className="btn btn-outline-light btn-lg"
              onClick={() => navigate("/problems")}
            >
              Explore Problems
            </button>
          </div>

        </div>
      </section>

      {/* STATS */}
      {/* <section className="stats-section container">
        <StatsCards />
      </section> */}

      {/* TOPICS */}
      <section className="topics-section container">
          <TopicsSection />
        {/* </div> */}

      </section>

      <FeaturedProblems />
      <Footer />
    </>
  );
}


function App() {

  // LOGIN STATE SHOULD BE HERE
  const [isLoggedIn, setIsLoggedIn] = useState(
    localStorage.getItem("token") !== null
  );

  return (
    <BrowserRouter>

      <Navbar
        isLoggedIn={isLoggedIn}
        setIsLoggedIn={setIsLoggedIn}
      />

      <main className="page-container">

        <Routes>

          <Route path="/" element={<Home />} />

          <Route path="/problems" element={<Problems />} />
          <Route path="/problems/:slug" element={<ProblemDetail />} />

          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />

          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/submissions" element={<Submissions />} />
          <Route path="/settings" element={<Settings />} />

          <Route path="/discuss" element={<Discuss />} />
          <Route path="/discuss/create" element={<CreatePost />} />
          <Route path="/discuss/:id" element={<PostDetail />} />

          <Route path="/verify-otp" element={<VerifyOtp />} />
          <Route path="/reset-password" element={<ResetPassword />} />

          <Route path="/learn" element={<LearnPage />} />
          <Route path="/learn/:slug" element={<LearnProblem />} />
          <Route path="/topic/:topic" element={<ProblemsByTopic />} />

        </Routes>

      </main>

    </BrowserRouter>
  );
}

export default App;