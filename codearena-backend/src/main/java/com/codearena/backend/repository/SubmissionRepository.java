// package com.codearena.backend.repository;

// import com.codearena.backend.entity.Submission;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import java.util.List;

// public interface SubmissionRepository extends JpaRepository<Submission, Long> {

//     // Fetch all submissions for a user
//     List<Submission> findByUserId(Long userId);

//     // Fetch all submissions for a problem
//     List<Submission> findByProblemId(Long problemId);

//     // Fetch submissions by user & problem
//     List<Submission> findByUserIdAndProblemId(Long userId, Long problemId);

//     // ✅ NEW: Fetch submissions for a problem (latest first)
//     List<Submission> findByProblemIdOrderByIdDesc(Long problemId);

//     // ✅ NEW: Fetch submissions for a user & problem (latest first)
//     List<Submission> findByUserIdAndProblemIdOrderByIdDesc(Long userId, Long problemId);

//      // Count DISTINCT accepted problems
//     @Query("""
//         SELECT COUNT(DISTINCT s.problemId)
//         FROM Submission s
//         WHERE s.userId = :userId
//         AND s.verdict = 'Accepted'
//     """)
//     long countDistinctAccepted(@Param("userId") Long userId);


//     // Count accepted problems by difficulty
//     @Query("""
//         SELECT COUNT(DISTINCT s.problemId)
//         FROM Submission s
//         JOIN Problem p ON s.problemId = p.id
//         WHERE s.userId = :userId
//         AND s.verdict = 'Accepted'
//         AND p.difficulty = :difficulty
//     """)
//     long countAcceptedByDifficulty(
//             @Param("userId") Long userId,
//             @Param("difficulty") String difficulty
//     );

//     List<Submission> findByUserIdAndVerdict(Long userId, String verdict);
// }
















package com.codearena.backend.repository;

import com.codearena.backend.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // Fetch all submissions for a user
    List<Submission> findByUserId(Long userId);

    // Fetch all submissions for a problem
    List<Submission> findByProblemId(Long problemId);

    // Fetch submissions by user & problem
    List<Submission> findByUserIdAndProblemId(Long userId, Long problemId);

    // ✅ NEW: Fetch submissions for a problem (latest first)
    List<Submission> findByProblemIdOrderByIdDesc(Long problemId);

    // ✅ NEW: Fetch submissions for a user & problem (latest first)
    List<Submission> findByUserIdAndProblemIdOrderByIdDesc(Long userId, Long problemId);

     // Count DISTINCT accepted problems
    @Query("""
        SELECT COUNT(DISTINCT s.problemId)
        FROM Submission s
        WHERE s.userId = :userId
        AND s.verdict = 'Accepted'
    """)
    long countDistinctAccepted(@Param("userId") Long userId);


    // Count accepted problems by difficulty
    @Query("""
        SELECT COUNT(DISTINCT s.problemId)
        FROM Submission s
        JOIN Problem p ON s.problemId = p.id
        WHERE s.userId = :userId
        AND s.verdict = 'Accepted'
        AND p.difficulty = :difficulty
    """)
    long countAcceptedByDifficulty(
            @Param("userId") Long userId,
            @Param("difficulty") String difficulty
    );

    List<Submission> findByUserIdAndVerdict(Long userId, String verdict);

    // Contest submissions for a user (used for the "my submissions" panel in the arena)
    List<Submission> findByContestIdAndUserIdOrderByIdDesc(Long contestId, Long userId);

    // Same, but scoped to one problem — this is what the CodeEditor's per-problem
    // submission history table actually needs.
    List<Submission> findByContestIdAndUserIdAndProblemIdOrderByIdDesc(Long contestId, Long userId, Long problemId);

    // Every ACCEPTED submission in a contest, oldest first — used to compute
    // "first AC per user per problem" in ContestService rather than in SQL,
    // since dedup-by-earliest is simpler to do in Java than in a portable query.
    @Query("""
        SELECT s FROM Submission s
        WHERE s.contestId = :contestId
        AND s.verdict = 'ACCEPTED'
        ORDER BY s.id ASC
    """)
    List<Submission> findAcceptedSubmissionsForContest(@Param("contestId") Long contestId);
}