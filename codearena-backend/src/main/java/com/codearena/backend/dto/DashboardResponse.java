package com.codearena.backend.dto;
public class DashboardResponse {

    private String username;
    private String email;

    private long totalSolved;
    private long easySolved;
    private long mediumSolved;
    private long hardSolved;

    public DashboardResponse(String username, String email,
                             long totalSolved,
                             long easySolved,
                             long mediumSolved,
                             long hardSolved) {
        this.username = username;
        this.email = email;
        this.totalSolved = totalSolved;
        this.easySolved = easySolved;
        this.mediumSolved = mediumSolved;
        this.hardSolved = hardSolved;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public long getTotalSolved() { return totalSolved; }
    public long getEasySolved() { return easySolved; }
    public long getMediumSolved() { return mediumSolved; }
    public long getHardSolved() { return hardSolved; }
}