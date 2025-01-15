package cricbuzz.service;

import cricbuzz.model.Match;
import cricbuzz.model.MatchStatus;

import java.util.HashMap;
import java.util.Map;

public class MatchService {
    private Map<String, Match> matches;

    public MatchService() {
        this.matches = new HashMap<>();
    }

    public void addMatch(Match match) {
        this.matches.put(match.getId(), match);
    }

    public Match getMatch(String id) {
        return this.matches.get(id);
    }

    public void updateMatchStatus(String matchId, MatchStatus matchStatus) {
        this.matches.get(matchId).updateMatchStatus(matchStatus);
    }
}
