import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BattleLeaderBoard.css';
import API_URL from "../../config";

function BattleLeaderBoard({ battle }) {
    const [leaderboard, setLeaderboard] = useState([]);
    const [isLeaderboardAvailable, setIsLeaderboardAvailable] = useState(true);

    useEffect(() => {
        if (battle) {
            axios.get(`${API_URL}/battle/${battle.battleId}/all`)
                .then(response => {
                    setLeaderboard(response.data);
                    setIsLeaderboardAvailable(true);
                })
                .catch(error => {
                    console.error('Error fetching leaderboard:', error);
                    setIsLeaderboardAvailable(false);
                });
        }
    }, [battle]);


    const renderLeaderboard = () => {
        return (
            <ul>
                {leaderboard.map((entry, index) => (
                    <li key={index}>
                        {index + 1}{getOrdinalSuffix(index + 1)} Position - {entry.groupId} - Points: {entry.scoreValue} - LinkRepo: {entry.link}
                    </li>
                ))}
            </ul>
        );
    };

    const getOrdinalSuffix = (n) => {
        const s = ["th", "st", "nd", "rd"],
            v = n % 100;
        return n+(s[(v-20)%10] || s[v] || s[0]);
    }

    return (
        <div className="tournament-leaderboard">
            {battle && (
                <>
                    <h2>
                        {battle.status === 'CLOSED' ?
                            'FINAL RANKING: ' : 'TEMPORARY RANKING: '}
                        {battle.name}
                    </h2>
                    {battle.status === 'PRE-BATTLE' && <p>The ranking is not available.</p>}
                    {(battle.status === 'ACTIVE' || battle.status === 'CLOSED' || battle.status === 'CLOSING') && isLeaderboardAvailable ?
                        renderLeaderboard():
                        <p>The ranking is not currently available.</p>
                    }
                </>
            )}
        </div>
    );
}

export default BattleLeaderBoard;



