import React from 'react';
import './BattleCardEducatormysection.css';

function BattleCardEducatormysection({ battle}) {
    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Description: {battle.description}</p>
            <p>Registration Deadline:{battle.registrationDeadline}</p>
            <p>Registration Deadline:{battle.submissionDeadline}</p>
        </div>
    );
}

export default BattleCardEducatormysection;
