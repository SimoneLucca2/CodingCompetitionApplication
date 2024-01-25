import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudentmysection.css';
import joinbattle from "../JoinBattle/joinbattle";
import quitbattle from "../QuitBattle/quitbattle";

function BattleCardStudentmysection({ battle}) {
    const navigate = useNavigate();

    function joinBattle(e) {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card
        navigate(`/joinbattle`);
    }

    function quitBattle(e) {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card
        navigate(`/quitbattle`);
    }

    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Dettagli: {battle.details}</p>
            <button className="join-button" onClick={joinBattle}>Join the Battle</button>
            <button className="join-button" onClick={quitBattle}>Quit the Battle</button>

            {/* Altri dettagli della battaglia possono essere aggiunti qui */}
        </div>
    );
}

export default BattleCardStudentmysection;
