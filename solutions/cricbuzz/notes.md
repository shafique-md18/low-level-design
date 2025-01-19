## Logging Framework

### Requirements

1. The Cricbuzz system should provide information about cricket matches, teams, players, and live scores.
2. Users should be able to view the schedule of upcoming matches and the results of completed matches.
3. The system should allow users to search for specific matches, teams, or players.
4. Users should be able to view detailed information about a particular match, including the scorecard, commentary, and statistics.
5. The system should support real-time updates of live scores and match information.
6. The system should handle concurrent access to match data and ensure data consistency.
7. The system should be scalable and able to handle a large volume of user requests.
8. The system should be extensible to accommodate new features and enhancements in the future.

#### P0 Requirements
1. System should be able to store information on Match, Teams, Players and live scores.
2. Option to view scorecard and match summary

#### P1 Requirements
1. Real time updates of live scores, commentary.
2. Concurrent access to match data.
3. Additional statistics.
4. Search feature for specific matches, teams and players and their statistics.
5. Schedules of upcoming matches and the results of completed matches.

### Class Diagram



### Other considerations:
1. How to properly take care of run/ball types like score and dismissals along with cricket rules?
2. How to support different types of matches like T20, ODI, etc.
