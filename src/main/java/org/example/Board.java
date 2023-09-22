package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

class Board {

    private final TreeSet<Match> positions;
    private final HashMap<Match.Id, Match> matches = new HashMap<>();

    public Board(Comparator<Match> matchComparator) {
        this.positions = new TreeSet<>(matchComparator);
    }

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
