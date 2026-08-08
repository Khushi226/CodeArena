// package com.codearena.backend.service;

// import com.codearena.backend.dto.*;
// import com.codearena.backend.entity.*;
// import com.codearena.backend.judge.CodeJudgeService;
// import com.codearena.backend.repository.*;
// import org.springframework.stereotype.Service;
// import org.springframework.web.server.ResponseStatusException;
// import org.springframework.http.HttpStatus;

// import java.time.Instant;
// import java.util.*;
// import java.util.stream.Collectors;

// @Service
// public class ContestService {

//     private final ContestRepository contestRepository;
//     private final ContestProblemRepository contestProblemRepository;
//     private final ContestParticipantRepository contestParticipantRepository;
//     private final SubmissionRepository submissionRepository;
//     private final ProblemService problemService;
//     private final UserRepository userRepository;
//     private final CodeJudgeService codeJudgeService;
//     private final SubmissionService submissionService;

//     public ContestService(ContestRepository contestRepository,
//                            ContestProblemRepository contestProblemRepository,
//                            ContestParticipantRepository contestParticipantRepository,
//                            SubmissionRepository submissionRepository,
//                            ProblemService problemService,
//                            UserRepository userRepository,
//                            CodeJudgeService codeJudgeService,
//                            SubmissionService submissionService) {
//         this.contestRepository = contestRepository;
//         this.contestProblemRepository = contestProblemRepository;
//         this.contestParticipantRepository = contestParticipantRepository;
//         this.submissionRepository = submissionRepository;
//         this.problemService = problemService;
//         this.userRepository = userRepository;
//         this.codeJudgeService = codeJudgeService;
//         this.submissionService = submissionService;
//     }

//     /* -------------------- status: always derived, never stored -------------------- */

//     public String getStatus(Contest contest) {
//         Instant now = Instant.now();
//         if (now.isBefore(contest.getStartTime())) return "UPCOMING";
//         if (now.isAfter(contest.getEndTime())) return "ENDED";
//         return "RUNNING";
//     }

//     /* -------------------- create (admin only — enforced in controller) -------------------- */

//     public ContestResponse createContest(ContestCreateRequest request, Long adminUserId) {
//         if (!request.getEndTime().isAfter(request.getStartTime())) {
//             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endTime must be after startTime");
//         }

//         Contest contest = new Contest();
//         contest.setTitle(request.getTitle());
//         contest.setDescription(request.getDescription());
//         contest.setStartTime(request.getStartTime());
//         contest.setEndTime(request.getEndTime());
//         contest.setCreatedByUserId(adminUserId);
//         contest.setSlug(slugify(request.getTitle()) + "-" + System.currentTimeMillis());
//         contest = contestRepository.save(contest);

//         List<ContestProblem> problems = new ArrayList<>();
//         for (ContestCreateRequest.ProblemEntry entry : request.getProblems()) {
//             ContestProblem cp = new ContestProblem();
//             cp.setContestId(contest.getId());
//             cp.setProblemId(entry.getProblemId());
//             cp.setPoints(entry.getPoints());
//             cp.setOrderIndex(entry.getOrderIndex());
//             problems.add(cp);
//         }
//         contestProblemRepository.saveAll(problems);

//         return toResponse(contest, problems, false);
//     }

//     /* -------------------- read -------------------- */

//     public ContestResponse getBySlug(String slug, Long requestingUserId) {
//         Contest contest = findBySlugOrThrow(slug);
//         List<ContestProblem> problems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(contest.getId());
//         boolean registered = requestingUserId != null
//                 && contestParticipantRepository.existsByContestIdAndUserId(contest.getId(), requestingUserId);
//         return toResponse(contest, problems, registered);
//     }

//     public List<ContestResponse> getAll(Long requestingUserId) {
//         return contestRepository.findAll().stream()
//                 .map(c -> {
//                     List<ContestProblem> problems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(c.getId());
//                     boolean registered = requestingUserId != null
//                             && contestParticipantRepository.existsByContestIdAndUserId(c.getId(), requestingUserId);
//                     return toResponse(c, problems, registered);
//                 })
//                 .collect(Collectors.toList());
//     }

//     /* -------------------- register -------------------- */

//     public void register(Long contestId, Long userId) {
//         Contest contest = findByIdOrThrow(contestId);

//         if (getStatus(contest).equals("ENDED")) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest has already ended");
//         }
//         if (contestParticipantRepository.existsByContestIdAndUserId(contestId, userId)) {
//             return; // already registered — idempotent, not an error
//         }

//         ContestParticipant participant = new ContestParticipant();
//         participant.setContestId(contestId);
//         participant.setUserId(userId);
//         contestParticipantRepository.save(participant);
//     }

//     /* -------------------- problem detail: gated by start time -------------------- */

//     public ProblemResponse getContestProblem(Long contestId, Long problemId, Long requestingUserId) {
//         Contest contest = findByIdOrThrow(contestId);

//         // Server-side gate. The frontend also hides this, but that's just UX —
//         // this check is what actually stops someone hitting the endpoint early.
//         if (Instant.now().isBefore(contest.getStartTime())) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest hasn't started yet");
//         }

//         contestProblemRepository.findByContestIdAndProblemId(contestId, problemId)
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem is not part of this contest"));

//         var problem = problemService.getProblemById(problemId);
//         String starterCode = com.codearena.backend.judge.driver.StarterCodeGenerator.generate(problemId);

//         return new ProblemResponse(
//                 problem.getId(),
//                 problem.getTitle(),
//                 problem.getDescription(),
//                 problem.getDifficulty(),
//                 problem.getExamples(),
//                 problem.getConstraints(),
//                 starterCode
//         );
//     }

//     /* -------------------- my submission history for one contest problem -------------------- */

//     public List<Submission> getMySubmissionsForProblem(Long contestId, Long problemId, Long userId) {
//         // No start-time gate needed here — if the user has a JWT and is asking about
//         // their own submissions, that's only possible after they've already interacted
//         // with a problem that was itself gated by getContestProblem()/submit() above.
//         return submissionRepository.findByContestIdAndUserIdAndProblemIdOrderByIdDesc(contestId, userId, problemId);
//     }

//     /* -------------------- submit: gated by start AND end time -------------------- */

//     public com.codearena.backend.dto.JudgeResponse submit(Long contestId, ContestSubmitRequest request, Long userId) {
//         Contest contest = findByIdOrThrow(contestId);
//         Instant now = Instant.now();

//         if (now.isBefore(contest.getStartTime())) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest hasn't started yet");
//         }
//         if (now.isAfter(contest.getEndTime())) {
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest has ended — submission rejected");
//         }

//         contestProblemRepository.findByContestIdAndProblemId(contestId, request.getProblemId())
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem is not part of this contest"));

//         var result = codeJudgeService.judgeJava(request.getProblemId(), request.getCode(), false, userId);

//         Submission submission = new Submission();
//         submission.setUserId(userId);
//         submission.setProblemId(request.getProblemId());
//         submission.setContestId(contestId);
//         submission.setLanguage(request.getLanguage());
//         submission.setCode(request.getCode());
//         submission.setVerdict(result.getResult().name());
//         submissionService.saveSubmission(submission);

//         return result;
//     }

//     /* -------------------- leaderboard -------------------- */

//     public List<LeaderboardEntry> getLeaderboard(Long contestId) {
//         List<ContestProblem> contestProblems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(contestId);
//         Map<Long, Integer> pointsByProblem = new HashMap<>();
//         for (ContestProblem cp : contestProblems) {
//             pointsByProblem.put(cp.getProblemId(), cp.getPoints());
//         }

//         List<Submission> accepted = submissionRepository.findAcceptedSubmissionsForContest(contestId);

//         // First accepted submission per (userId, problemId) — ordered ASC by id already,
//         // so the first time we see a (user, problem) pair in the loop IS their earliest AC.
//         Map<Long, Map<Long, Submission>> firstAcByUser = new HashMap<>();
//         for (Submission s : accepted) {
//             firstAcByUser
//                     .computeIfAbsent(s.getUserId(), k -> new HashMap<>())
//                     .putIfAbsent(s.getProblemId(), s);
//         }

//         List<LeaderboardEntry> entries = new ArrayList<>();
//         for (Map.Entry<Long, Map<Long, Submission>> userEntry : firstAcByUser.entrySet()) {
//             Long userId = userEntry.getKey();
//             Map<Long, Submission> solvedProblems = userEntry.getValue();

//             int totalScore = 0;
//             var finishTime = java.time.LocalDateTime.MIN;
//             for (Map.Entry<Long, Submission> pe : solvedProblems.entrySet()) {
//                 totalScore += pointsByProblem.getOrDefault(pe.getKey(), 0);
//                 if (pe.getValue().getCreatedAt().isAfter(finishTime)) {
//                     finishTime = pe.getValue().getCreatedAt();
//                 }
//             }

//             String username = userRepository.findById(userId)
//                     .map(User::getUsername)
//                     .orElse("unknown");

//             entries.add(new LeaderboardEntry(0, userId, username, totalScore, finishTime));
//         }

//         entries.sort((a, b) -> {
//             if (b.getTotalScore() != a.getTotalScore()) {
//                 return Integer.compare(b.getTotalScore(), a.getTotalScore());
//             }
//             return a.getFinishTime().compareTo(b.getFinishTime()); // earlier finish wins tiebreak
//         });

//         List<LeaderboardEntry> ranked = new ArrayList<>();
//         for (int i = 0; i < entries.size(); i++) {
//             LeaderboardEntry e = entries.get(i);
//             ranked.add(new LeaderboardEntry(i + 1, e.getUserId(), e.getUsername(), e.getTotalScore(), e.getFinishTime()));
//         }
//         return ranked;
//     }

//     /* -------------------- helpers -------------------- */

//     private Contest findBySlugOrThrow(String slug) {
//         return contestRepository.findBySlug(slug)
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found"));
//     }

//     private Contest findByIdOrThrow(Long id) {
//         return contestRepository.findById(id)
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found"));
//     }

//     private ContestResponse toResponse(Contest contest, List<ContestProblem> problems, boolean registered) {
//         List<ContestProblemSummary> summaries = problems.stream()
//                 .map(cp -> new ContestProblemSummary(
//                         cp.getProblemId(),
//                         problemService.getProblemById(cp.getProblemId()).getTitle(),
//                         cp.getPoints(),
//                         cp.getOrderIndex()
//                 ))
//                 .collect(Collectors.toList());

//         return new ContestResponse(
//                 contest.getId(),
//                 contest.getSlug(),
//                 contest.getTitle(),
//                 contest.getDescription(),
//                 contest.getStartTime(),
//                 contest.getEndTime(),
//                 getStatus(contest),
//                 registered,
//                 summaries
//         );
//     }

//     private String slugify(String title) {
//         return title.toLowerCase().trim().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
//     }
// }


















package com.codearena.backend.service;

import com.codearena.backend.dto.*;
import com.codearena.backend.entity.*;
import com.codearena.backend.judge.CodeJudgeService;
import com.codearena.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContestService {

    private final ContestRepository contestRepository;
    private final ContestProblemRepository contestProblemRepository;
    private final ContestParticipantRepository contestParticipantRepository;
    private final SubmissionRepository submissionRepository;
    private final ProblemService problemService;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final CodeJudgeService codeJudgeService;
    private final SubmissionService submissionService;

    public ContestService(ContestRepository contestRepository,
                           ContestProblemRepository contestProblemRepository,
                           ContestParticipantRepository contestParticipantRepository,
                           SubmissionRepository submissionRepository,
                           ProblemService problemService,
                           ProblemRepository problemRepository,
                           UserRepository userRepository,
                           CodeJudgeService codeJudgeService,
                           SubmissionService submissionService) {
        this.contestRepository = contestRepository;
        this.contestProblemRepository = contestProblemRepository;
        this.contestParticipantRepository = contestParticipantRepository;
        this.submissionRepository = submissionRepository;
        this.problemService = problemService;
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.codeJudgeService = codeJudgeService;
        this.submissionService = submissionService;
    }

    public String getStatus(Contest contest) {
        Instant now = Instant.now();
        if (now.isBefore(contest.getStartTime())) return "UPCOMING";
        if (now.isAfter(contest.getEndTime())) return "ENDED";
        return "RUNNING";
    }

    public ContestResponse createContest(ContestCreateRequest request, Long adminUserId) {
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endTime must be after startTime");
        }

        Contest contest = new Contest();
        contest.setTitle(request.getTitle());
        contest.setDescription(request.getDescription());
        contest.setStartTime(request.getStartTime());
        contest.setEndTime(request.getEndTime());
        contest.setCreatedByUserId(adminUserId);
        contest.setSlug(slugify(request.getTitle()) + "-" + System.currentTimeMillis());
        contest = contestRepository.save(contest);

        List<ContestProblem> problems = new ArrayList<>();
        for (ContestCreateRequest.ProblemEntry entry : request.getProblems()) {
            ContestProblem cp = new ContestProblem();
            cp.setContestId(contest.getId());
            cp.setProblemId(entry.getProblemId());
            cp.setPoints(entry.getPoints());
            cp.setOrderIndex(entry.getOrderIndex());
            problems.add(cp);
        }
        contestProblemRepository.saveAll(problems);

        return toResponse(contest, problems, false);
    }

    public ContestResponse getBySlug(String slug, Long requestingUserId) {
        Contest contest = findBySlugOrThrow(slug);
        revealProblemsIfEnded(contest);
        List<ContestProblem> problems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(contest.getId());
        boolean registered = requestingUserId != null
                && contestParticipantRepository.existsByContestIdAndUserId(contest.getId(), requestingUserId);
        return toResponse(contest, problems, registered);
    }

    public List<ContestResponse> getAll(Long requestingUserId) {
        return contestRepository.findAll().stream()
                .map(c -> {
                    List<ContestProblem> problems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(c.getId());
                    boolean registered = requestingUserId != null
                            && contestParticipantRepository.existsByContestIdAndUserId(c.getId(), requestingUserId);
                    return toResponse(c, problems, registered);
                })
                .collect(Collectors.toList());
    }

    public void register(Long contestId, Long userId) {
        Contest contest = findByIdOrThrow(contestId);

        if (getStatus(contest).equals("ENDED")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest has already ended");
        }
        if (contestParticipantRepository.existsByContestIdAndUserId(contestId, userId)) {
            return;
        }

        ContestParticipant participant = new ContestParticipant();
        participant.setContestId(contestId);
        participant.setUserId(userId);
        contestParticipantRepository.save(participant);
    }

    public ProblemResponse getContestProblem(Long contestId, Long problemId, Long requestingUserId) {
        Contest contest = findByIdOrThrow(contestId);

        if (Instant.now().isBefore(contest.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest hasn't started yet");
        }

        contestProblemRepository.findByContestIdAndProblemId(contestId, problemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem is not part of this contest"));

        var problem = problemService.getProblemById(problemId);
        String starterCode = com.codearena.backend.judge.driver.StarterCodeGenerator.generate(problemId);

        return new ProblemResponse(
                problem.getId(),
                problem.getTitle(),
                problem.getDescription(),
                problem.getDifficulty(),
                problem.getExamples(),
                problem.getConstraints(),
                starterCode
        );
    }

    public List<Submission> getMySubmissionsForProblem(Long contestId, Long problemId, Long userId) {
        return submissionRepository.findByContestIdAndUserIdAndProblemIdOrderByIdDesc(contestId, userId, problemId);
    }

    public com.codearena.backend.dto.JudgeResponse submit(Long contestId, ContestSubmitRequest request, Long userId) {
        Contest contest = findByIdOrThrow(contestId);
        Instant now = Instant.now();

        if (now.isBefore(contest.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest hasn't started yet");
        }
        if (now.isAfter(contest.getEndTime())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contest has ended — submission rejected");
        }

        contestProblemRepository.findByContestIdAndProblemId(contestId, request.getProblemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem is not part of this contest"));

        var result = codeJudgeService.judgeJava(request.getProblemId(), request.getCode(), false, userId);

        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setProblemId(request.getProblemId());
        submission.setContestId(contestId);
        submission.setLanguage(request.getLanguage());
        submission.setCode(request.getCode());
        submission.setVerdict(result.getResult().name());
        submissionService.saveSubmission(submission);

        return result;
    }

    public List<LeaderboardEntry> getLeaderboard(Long contestId) {
        revealProblemsIfEnded(findByIdOrThrow(contestId));
        List<ContestProblem> contestProblems = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(contestId);
        Map<Long, Integer> pointsByProblem = new HashMap<>();
        for (ContestProblem cp : contestProblems) {
            pointsByProblem.put(cp.getProblemId(), cp.getPoints());
        }

        List<Submission> accepted = submissionRepository.findAcceptedSubmissionsForContest(contestId);

        Map<Long, Map<Long, Submission>> firstAcByUser = new HashMap<>();
        for (Submission s : accepted) {
            firstAcByUser
                    .computeIfAbsent(s.getUserId(), k -> new HashMap<>())
                    .putIfAbsent(s.getProblemId(), s);
        }

        List<LeaderboardEntry> entries = new ArrayList<>();
        for (Map.Entry<Long, Map<Long, Submission>> userEntry : firstAcByUser.entrySet()) {
            Long userId = userEntry.getKey();
            Map<Long, Submission> solvedProblems = userEntry.getValue();

            int totalScore = 0;
            var finishTime = java.time.LocalDateTime.MIN;
            for (Map.Entry<Long, Submission> pe : solvedProblems.entrySet()) {
                totalScore += pointsByProblem.getOrDefault(pe.getKey(), 0);
                if (pe.getValue().getCreatedAt().isAfter(finishTime)) {
                    finishTime = pe.getValue().getCreatedAt();
                }
            }

            String username = userRepository.findById(userId)
                    .map(User::getUsername)
                    .orElse("unknown");

            entries.add(new LeaderboardEntry(0, userId, username, totalScore, finishTime));
        }

        entries.sort((a, b) -> {
            if (b.getTotalScore() != a.getTotalScore()) {
                return Integer.compare(b.getTotalScore(), a.getTotalScore());
            }
            return a.getFinishTime().compareTo(b.getFinishTime());
        });

        List<LeaderboardEntry> ranked = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            LeaderboardEntry e = entries.get(i);
            ranked.add(new LeaderboardEntry(i + 1, e.getUserId(), e.getUsername(), e.getTotalScore(), e.getFinishTime()));
        }
        return ranked;
    }

    public void revealProblemsIfEnded(Contest contest) {
        if (!getStatus(contest).equals("ENDED")) return;

        List<ContestProblem> cps = contestProblemRepository.findByContestIdOrderByOrderIndexAsc(contest.getId());
        for (ContestProblem cp : cps) {
            problemRepository.findById(cp.getProblemId()).ifPresent(problem -> {
                if (!problem.isVisible()) {
                    problem.setVisible(true);
                    problemRepository.save(problem);
                }
            });
        }
    }

    public void revealAllEndedContests() {
        List<Contest> ended = contestRepository.findByEndTimeBefore(Instant.now());
        for (Contest contest : ended) {
            revealProblemsIfEnded(contest);
        }
    }

    private Contest findBySlugOrThrow(String slug) {
        return contestRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found"));
    }

    private Contest findByIdOrThrow(Long id) {
        return contestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contest not found"));
    }

    private ContestResponse toResponse(Contest contest, List<ContestProblem> problems, boolean registered) {
        List<ContestProblemSummary> summaries = problems.stream()
                .map(cp -> new ContestProblemSummary(
                        cp.getProblemId(),
                        problemService.getProblemById(cp.getProblemId()).getTitle(),
                        cp.getPoints(),
                        cp.getOrderIndex()
                ))
                .collect(Collectors.toList());

        return new ContestResponse(
                contest.getId(),
                contest.getSlug(),
                contest.getTitle(),
                contest.getDescription(),
                contest.getStartTime(),
                contest.getEndTime(),
                getStatus(contest),
                registered,
                summaries
        );
    }

    private String slugify(String title) {
        return title.toLowerCase().trim().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }
}