package cricbuzz;

import cricbuzz.model.*;
import cricbuzz.service.MatchService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Player playerA = new Player.Builder().withId("A").build();
        Player playerB = new Player.Builder().withId("B").build();
        Player playerC = new Player.Builder().withId("C").build();
        Player playerD = new Player.Builder().withId("D").build();
        Player playerE = new Player.Builder().withId("E").build();
        Player playerF = new Player.Builder().withId("F").build();

        Map<String, Player> teamIndiaPlayers = new HashMap<>();
        teamIndiaPlayers.put(playerA.getId(), playerA);
        teamIndiaPlayers.put(playerB.getId(), playerB);
        teamIndiaPlayers.put(playerC.getId(), playerC);
        Map<String, Player> teamAustraliaPlayers = new HashMap<>();
        teamIndiaPlayers.put(playerA.getId(), playerA);
        teamIndiaPlayers.put(playerB.getId(), playerB);
        teamIndiaPlayers.put(playerC.getId(), playerC);

        Team teamIndia = new Team.Builder().withPlayingEleven(teamIndiaPlayers).build();
        Team teamAustralia = new Team.Builder().withPlayingEleven(teamAustraliaPlayers).build();

        Inning firstInning = new Inning.Builder().withBattingTeam(teamIndia).withBowlingTeam(teamAustralia).build();
        Inning secondInning = new Inning.Builder().withBattingTeam(teamAustralia).withBowlingTeam(teamIndia).build();


        Match match = new Match.Builder()
                .withId(UUID.randomUUID().toString())
                .withStatus(MatchStatus.ONGOING)
                .withInnings(Arrays.asList(firstInning, secondInning))
                .withTeams(Arrays.asList(teamIndia, teamAustralia))
                .build();

        MatchService matchService = new MatchService();
        matchService.addMatch(match);

        // First Inning
        firstInning.setStrikerBatsman(playerA);
        firstInning.setNonStrikerBatsman(playerB);
        firstInning.setBowler(playerD);
        firstInning.addOver(getOver(playerD, playerA));
        firstInning.rotateStrike();
        firstInning.addOver(getOver(playerF, playerB));

        firstInning.getScorecard().displayScorecard();

        // Second Inning
        secondInning.setStrikerBatsman(playerD);
        secondInning.setNonStrikerBatsman(playerE);
        secondInning.setBowler(playerC);
        secondInning.addOver(getOver(playerD, playerC));
        secondInning.rotateStrike();
        secondInning.addOver(getOver(playerE, playerB));

        secondInning.getScorecard().displayScorecard();

        matchService.updateMatchStatus(match.getId(), MatchStatus.COMPLETED);
    }

    private static Over getOver(Player bowler, Player batter) {
        Over over = new Over.Builder().withBalls(new ArrayList<>()).build();

        for (int i = 1; i <= 6; i++) {
            over.addBall(new Ball.Builder()
                    .withId(UUID.randomUUID().toString())
                    .withBatsman(batter).withBallType(BallType.VALID)
                    .withRunType(RunType.ONE)
                    .withBallNumber(i).build());
        }

        return over;
    }
}
