package AlgoExpert.Easy;
import javax.print.DocFlavor;
import java.util.*;


public class TournamentWinner {
    // O(n) time | O(k) space where n is the number of
    // competitions and k is the number of teams
    int HOME_TEAM_WON = 1;
    public String tournamentWinner (ArrayList<ArrayList<String>> competitions,
                                    ArrayList<Integer> results) {
        String curBestTeam = "";
        HashMap<String, Integer> scores = new HashMap<>();

        scores.put(curBestTeam, 0);

        for (int idx = 0; idx < competitions.size(); idx++) {
            ArrayList<String> competition = competitions.get(idx);
            int result = results.get(idx);

            String homeTeam = competition.get(0);
            String awayTeam = competition.get(1);

            String winningTeam = (result == HOME_TEAM_WON) ?
                    homeTeam : awayTeam;

            updateScores(winningTeam, 3, scores);

            if (scores.get(winningTeam) > scores.get(curBestTeam)) {
                curBestTeam = winningTeam;
            }
        }
        return  curBestTeam;
    }

    private void updateScores(String winningTeam,
                              int points,
                              HashMap<String, Integer> scores) {
        if (!scores.containsKey(winningTeam)) scores.put(winningTeam, 0);

        scores.put(winningTeam, scores.get(winningTeam) + points);
    }
}
