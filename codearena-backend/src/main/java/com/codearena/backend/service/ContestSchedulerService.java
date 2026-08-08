// package com.codearena.backend.service;

// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;

// @Service
// public class ContestSchedulerService {

//     private final ContestService contestService;

//     public ContestSchedulerService(ContestService contestService) {
//         this.contestService = contestService;
//     }

//     // Runs every 60s. This is the primary reveal mechanism — it fires on a
//     // clock regardless of site traffic. ContestService.getBySlug/getLeaderboard
//     // also do the same check inline as a backstop for the rare case where
//     // someone loads a just-ended contest before this tick runs.
//     @Scheduled(fixedRate = 60000)
//     public void revealEndedContestProblems() {
//         contestService.revealAllEndedContests();
//     }
// }