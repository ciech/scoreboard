package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Represents a board where matches can be added and retrieved.
 */
class Board {

    private final TreeSet<Match> positions;
    private final HashMap<Match.Id, Match> matches = new HashMap<>();

    /**
     * Constructor to create a Board with a specific match comparator.
     * @param matchComparator Comparator used for sorting the matches.
     */
    public Board(Comparator<Match> matchComparator) {
        this.positions = new TreeSet<>(matchComparator);
    }

    /**
     * Add a match to the board.
     * @param match The match to be added.
     * @throws IllegalStateException if the match is already on the board.
     */
    public void put(final Match match) {
        if (matches.containsKey(match.getId())) {
            throw new IllegalStateException("item already on board");
        }
        positions.add(match);
        matches.put(match.getId(), match);
    }

    public Match pull(final Match match) {
        return pull(match.getId());
    }

    /**
     * Remove a match from the board using its ID.
     * @param id The ID of the match to be removed.
     * @return The removed match.
     * @throws IllegalStateException if there is no match with the given ID on the board.
     */
    public Match pull(Match.Id id) {
        if (!matches.containsKey(id)) {
            throw new IllegalStateException("no such item");
        }
        final var match = matches.remove(id);
        positions.remove(match);
        return match;
    }

    public List<Match> getAll(){
        return positions.stream().toList();
    }



}
