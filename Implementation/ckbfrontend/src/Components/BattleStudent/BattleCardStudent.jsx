import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudent.css';
import joinbattle from "../JoinBattle/joinbattle";
import quitbattle from "../QuitBattle/quitbattle";

function BattleCardStudent({ battle}) {
    const navigate = useNavigate();

    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Dettagli: {battle.details}</p>

            {/* Altri dettagli della battaglia possono essere aggiunti qui */}
        </div>
    );
}

export default BattleCardStudent;
