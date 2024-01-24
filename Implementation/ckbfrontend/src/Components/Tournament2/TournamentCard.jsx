import React from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCard.css';

function TournamentCard({ tournament }) {
    const navigate = useNavigate();

    const goToTournamentBattles = () => {
        navigate(`/tournaments/${tournament.id}/battles`);
    };

    const joinTournament = (e) => {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card
        navigate(`/jointournament/${tournament.id}`);
    };

    return (
        <div className="tournament-card" onClick={goToTournamentBattles}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <button className="join-button" onClick={joinTournament}>Join the Tournament</button>
        </div>
    );
}

export default TournamentCard;
