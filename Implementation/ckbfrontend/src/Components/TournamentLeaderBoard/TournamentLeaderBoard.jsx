import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './TournamentLeaderBoard.css';
import API_URL from "../../config";

function TournamentLeaderboard({ tournament }) {
    const [leaderboard, setLeaderboard] = useState([]);
    const [isLeaderboardAvailable, setIsLeaderboardAvailable] = useState(true);
    const tourId = tournament.tournamentId;
    const tournamentId = parseInt(tourId, 10);

    useEffect(() => {
        if (tournament) {
            axios.get(`${API_URL}/tournament/ranking/${tournamentId}`)
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


    const renderLeaderboard = () => {
        return (
            <ul>
                {leaderboard.map((entry, index) => (
                    <li key={index}>
                        {index + 1}{getOrdinalSuffix(index + 1)} Position - {entry.studentId} - Points: {entry.scoreValue}
                    </li>
                ))}
            </ul>
        );
    };

    const getOrdinalSuffix = (n) => {
        const s = ["th", "st", "nd", "rd"];
        const v = n % 100;

        if (v > 10 && v < 14) {
            return n + "th";
        }

        return (s[(n % 10)] || "th");
    }

    return (
        <div className="tournament-leaderboard">
            {tournament && (
                <>
                    <h2>
                        {tournament.status === 'CLOSED' ?
                            'FINAL RANKING: ' : 'TEMPORARY RANKING: '}
                        {tournament.name}
                    </h2>
                    {tournament.status === 'PREPARATION' && <p>The ranking is not available.</p>}
                    {((tournament.status === 'ACTIVE' || tournament.status === 'CLOSED' || tournament.status === 'CLOSING') && isLeaderboardAvailable) ?
                        renderLeaderboard():
                        <p>The ranking is not currently available.</p>
                    }
                </>
            )}
        </div>
    );
}

export default TournamentLeaderboard;



