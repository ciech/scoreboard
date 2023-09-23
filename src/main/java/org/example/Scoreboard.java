package org.example;

import java.util.List;

public interface Scoreboard {

     /**
      * Start a match and add it to the scoreboard.
      * @param home Home team name.
      * @param away Away team name.
      * @throws IllegalArgumentException if one of the teams is already on the board.
      */
     void startMatch(String home, String away);

     /**
      * Update the score for a specific match.
      * @param home Home team name.
      * @param away Away team name.
      * @param scoreHome Home team's score.
      * @param scoreAway Away team's score.
      * @throws IllegalStateException if the match is not found on the board.
      */
     void updateScore(String home, String away, int scoreHome, int scoreAway);

     /**
      * Finish a match and remove it from the scoreboard.
      * @param home Home team name.
      * @param away Away team name.
      * @throws IllegalStateException if the match is not found on the board.
      */
     void finishMatch(String home, String away);


     List<MatchDto> getMatchSummary();
}
