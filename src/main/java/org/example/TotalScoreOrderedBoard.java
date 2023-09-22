package org.example;

import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


class TotalScoreOrderedBoard implements Scoreboard {

    private final Board board = new Board(totalScoreBasedComparator());
    private final Set<String> teams = new TreeSet<>();
    private final Clock clock ;

    public TotalScoreOrderedBoard(Clock clock) {
        this.clock = clock;
    }

    public void startMatch(final String home, final String away) {
        if (!teams.add(home) || !teams.add(away)) {
            throw new IllegalArgumentException("one of the teams already on board");
        }
        final var match = new Match(home, away, Instant.now(clock));
        board.put(match);
    }
    public void updateScore(final String home, final String away, final int scoreHome, final int scoreAway) {
        final var id = Match.id(home, away);
        final var match = board.pull(id);
        match.updateScore(scoreHome, scoreAway);
        board.put(match);
    }
    public void finishMatch(final String home, final String away) {
        final var id = Match.id(home, away);
        board.pull(id);
        teams.remove(home);
        teams.remove(away);
    }
    public List<MatchDto> getMatchSummary() {
        return board.getAll().stream().map(Match::toDto).toList();
    }
    private static Integer matchTotalScore(final Match match) {
        return match.getHomeScore() + match.getAwayScore();
    }
    private static Comparator<Match> totalScoreBasedComparator() {
        return (Match match1, Match match2) -> {
            final var scoreComparison =  matchTotalScore(match2).compareTo(matchTotalScore(match1));
            if (scoreComparison == 0) {
                return match2.getStartTime().compareTo(match1.getStartTime());
            }
            return scoreComparison;
        };
    }

}
