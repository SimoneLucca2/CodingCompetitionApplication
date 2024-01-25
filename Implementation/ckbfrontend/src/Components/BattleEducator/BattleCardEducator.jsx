import React from 'react';
import './BattleCardEducator.css';

function BattleCardEducator({ battle}) {
    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Dettagli: {battle.details}</p>
            {/* Altri dettagli della battaglia possono essere aggiunti qui */}
        </div>
    );
}

export default BattleCardEducator;
