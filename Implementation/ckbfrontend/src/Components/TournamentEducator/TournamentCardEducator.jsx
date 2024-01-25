import React from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducator.css';

function TournamentCardEducator({ tournament }) {
    const navigate = useNavigate();

    const goToTournamentBattles = () => {
        navigate(`/battlespageeducator`);
    };

    return (
        <div className="tournament-card" onClick={goToTournamentBattles}>
            <h3>{tournament.name}</h3>
            <p>{tournament.tournamentId}</p>
        </div>
    );
}

export default TournamentCardEducator;
