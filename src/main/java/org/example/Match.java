package org.example;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a match between two teams.
 */
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

    /**
     * Constructor to create a Match object.
     * @param home The home team name.
     * @param away The away team name.
     * @param startTime The start time of the match.
     * @throws IllegalArgumentException if the home or away name is invalid.
     */
    public Match(final String home, final String away, final Instant startTime) {
        this.home = checkName(home);
        this.away = checkName(away);
        this.startTime = Objects.requireNonNull(startTime);
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

    /**
     * Update the scores for the teams.
     * @param homeScore The updated score for the home team.
     * @param awayScore The updated score for the away team.
     * @throws IllegalArgumentException if the provided scores are invalid.
     */
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
