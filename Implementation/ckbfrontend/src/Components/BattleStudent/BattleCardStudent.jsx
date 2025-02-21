import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudent.css';


function BattleCardStudent({ battle, onLeaderboardSelect}) {
    const handleLeaderboardClick = (e) => {
        e.stopPropagation();
        onLeaderboardSelect(battle);
    };
    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Description: {battle.description}</p>
            <p>Registration Deadline:{battle.registrationDeadline}</p>
            <p>Submission Deadline:{battle.submissionDeadline}</p>
            <p>Status:{battle.status}</p>

            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default BattleCardStudent;
