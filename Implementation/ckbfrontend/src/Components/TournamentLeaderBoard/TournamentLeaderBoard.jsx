/*import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './TournamentLeaderBoard.css';
import API_URL from "../../config";

function TournamentLeaderboard({ tournament }) {
    const [leaderboard, setLeaderboard] = useState([]);
    const [isLeaderboardAvailable, setIsLeaderboardAvailable] = useState(true);

    useEffect(() => {
        if (tournament) {
            axios.get(`${API_URL}/tournament/leaderboard/${tournament.tournamentId}`)
                .then(response => {
                    setLeaderboard(response.data);
                    setIsLeaderboardAvailable(true);
                })
                .catch(error => {
                    console.error('Error fetching leaderboard:', error);
                    setIsLeaderboardAvailable(false);
                });
        }
    }, [tournament]);

    const mockLeaderboardData = [
        { userName: 'Team Alpha', points: 120 },
        { userName: 'Team Beta', points: 105 },
        { userName: 'Team Gamma', points: 98 },
        { userName: 'Team Delta', points: 87 },
        { userName: 'Team Epsilon', points: 79 }
    ];

    useEffect(() => {
        if (tournament) {
            // Simulate fetching data from backend
            setTimeout(() => {
                setLeaderboard(mockLeaderboardData);
            }, 1000); // Simulate network delay
        }
    }, [tournament]);

    const renderLeaderboard = () => {
        return (
            <ul>
                {leaderboard.map((entry, index) => (
                    <li key={index}>{entry.userName} - Points: {entry.points}</li>
                ))}
            </ul>
        );
    };

    return (
        <div className="tournament-leaderboard">
            {tournament && (
                <>
                    <h2>
                        {tournament.status === 'CLOSED' || tournament.status === 'CLOSING' ?
                            'Provisional Ranking:' : 'Tournament Ranking:'}
                        {tournament.name}
                    </h2>
                    {tournament.status === 'PREPARATION' && <p>The ranking is not available.</p>}
                    {(tournament.status === 'ACTIVE' || tournament.status === 'CLOSED' || tournament.status === 'CLOSING') && isLeaderboardAvailable ?
                        renderLeaderboard() :
                        <p>The ranking is not currently available.</p>
                    }
                </>
            )}
        </div>
    );
}

export default TournamentLeaderboard;*/

import React, { useEffect, useState } from 'react';
import './TournamentLeaderBoard.css';

function TournamentLeaderBoard({ tournament }) {
    const [leaderboard, setLeaderboard] = useState([]);

    // Mock leaderboard data
    const mockLeaderboardData = [
        { userName: 'Team Alpha', points: 120 },
        { userName: 'Team Beta', points: 105 },
        { userName: 'Team Gamma', points: 98 },
        { userName: 'Team Delta', points: 87 },
        { userName: 'Team Epsilon', points: 79 }
    ];

    useEffect(() => {
        if (tournament) {
            // Simulate fetching data from backend
            setTimeout(() => {
                setLeaderboard(mockLeaderboardData);
            }, 1000); // Simulate network delay
        }
    }, [tournament]);

    const renderLeaderboard = () => {
        return (
            <ul>
                {leaderboard.map((entry, index) => (
                    <li key={index}>{entry.userName} - Points: {entry.points}</li>
                ))}
            </ul>
        );
    };

    return (
        <div className="tournament-leaderboard">
            {tournament && (
                <>
                    <h2>
                        {tournament.status === 'ACTIVE' || tournament.status === 'PREPARATION' || tournament.status === 'CLOSING' ?
                            'PROVISIONAL RANKING:' : 'FINAL RANKING OF THE TOURNAMENT:'}
                        {tournament.name}
                    </h2>
                    {(tournament.status === 'ACTIVE' || tournament.status === 'CLOSING' || tournament.status === 'CLOSED') ?
                        renderLeaderboard() :
                        <p>The ranking is not currently available.</p>
                    }
                </>
            )}
        </div>
    );
}

export default TournamentLeaderBoard;

