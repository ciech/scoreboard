package org.example;

import java.util.List;

public interface Scoreboard {

     void startMatch(String home, String away);
     void updateScore(String home, String away, int scoreHome, int scoreAway);
     void finishMatch(String home, String away);


     List<MatchDto> getMatchSummary();
}
