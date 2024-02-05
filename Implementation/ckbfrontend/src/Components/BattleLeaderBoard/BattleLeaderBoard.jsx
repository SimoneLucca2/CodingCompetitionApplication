import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BattleLeaderBoard.css';
import API_URL from "../../config";

function BattleLeaderBoard({ battle }) {
    const [leaderboard, setLeaderboard] = useState([]);
    const [isLeaderboardAvailable, setIsLeaderboardAvailable] = useState(true);
    const batId = battle.battleId;
    const battleId = parseInt(batId, 10);

    useEffect(() => {
        if (battle) {
            axios.get(`${API_URL}/battle/score/${battleId}/all`)
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
                        {index + 1}{getOrdinalSuffix(index + 1)} Position - Group: {entry.groupId} - Points: {entry.score} - LinkRepo: {entry.clonedRepositoryLink}
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
        <div className="battle-leaderboard">
            {battle && (
                <>
                    <h2>
                        {battle.status === 'CLOSED' ?
                            'FINAL RANKING: ' : 'TEMPORARY RANKING: '}
                        {battle.name}
                    </h2>
                    {battle.status === 'PRE-BATTLE' && <p>The ranking is not available.</p>}
                    {(battle.status === 'BATTLE' || battle.status === 'CLOSED' || battle.status === 'CONSOLIDATION') && isLeaderboardAvailable ?
                        renderLeaderboard():
                        <p>The ranking is not currently available.</p>
                    }
                </>
            )}
        </div>
    );
}

export default BattleLeaderBoard;



