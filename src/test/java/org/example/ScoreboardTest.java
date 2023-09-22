package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setup() {
        scoreboard = new TotalScoreOrderedBoard(Clock.systemUTC());
    }

    @Test
    void cannotStartMatchIfAlreadyStarted() {
        scoreboard.startMatch("Poland", "Holland");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Poland", "Holland"));
    }

    @Test
    void cannotHasMoreThanOneMatch() {
        scoreboard.startMatch("Poland", "Holland");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Poland", "England"));
    }

    @Test
    void cannotUpdateScoreIfNoMatch() {
        assertThrows(IllegalStateException.class, () -> scoreboard.updateScore("Poland", "Holland", 0, 1));

    }

    @Test
    void cannotFinishIfNoMatch() {
        assertThrows(IllegalStateException.class, () -> scoreboard.finishMatch("Poland", "Holland"));

    }
    private record TestMatch(String home, String away, int homeScore, int awayScore) {}

    @Test
    void scoreboardSorting() {
        final var scores = List.of(
            new TestMatch("Mexico", "Canada", 0, 5),
            new TestMatch("Spain", "Brazil", 10, 2),
            new TestMatch("Germany", "France", 2, 2),
            new TestMatch("Uruguay", "Italy", 6, 6),
            new TestMatch("Argentina", "Australia", 3, 1)
        );

        for(final var score: scores) {
            scoreboard.startMatch(score.home, score.away);
            scoreboard.updateScore(score.home, score.away, score.homeScore, score.awayScore);
        }

        final var summary = scoreboard.getMatchSummary();
        final var expectedHomeScoresInOrder = List.of("Uruguay", "Spain", "Mexico", "Argentina", "Germany");
        assertIterableEquals(expectedHomeScoresInOrder, summary.stream().map(MatchDto::home).toList());
    }
}