package org.example;

import java.time.Instant;
import java.util.Objects;


class Match {

    public record Id(String value) {
    }
    private final String home;
    private final String away;

    private final Instant startTime;
    
    private int homeScore = 0;
    private int awayScore = 0;

    private static String checkName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException(String.format("Invalid name %s", name));
        }
        return name;
    }
    private static int checkScore(int newScore, int score, String type) {
        if (newScore - score < 0) {
            throw new IllegalArgumentException(String.format("%s, invalid score", type));
        }
        return newScore;
    }
    public Match(final String home, final String away, final Instant startTime) {
        this.home = checkName(home);
        this.away = checkName(away);
        this.startTime = startTime;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void updateScore(int homeScore, int awayScore) {
        this.homeScore = checkScore(homeScore, this.homeScore, "home");
        this.awayScore = checkScore(awayScore, this.awayScore, "away");
    }

    public MatchDto toDto() {
        return new MatchDto(home, away, homeScore, awayScore);
    }

    public Id getId() {
        return id(this.home, this.away);
    }

    public static Id id(String home, String away) {
        return new Id(String.join("-", home, away));
    }

}
