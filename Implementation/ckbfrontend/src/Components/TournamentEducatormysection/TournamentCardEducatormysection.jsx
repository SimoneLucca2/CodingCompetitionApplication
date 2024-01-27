import React from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducatormysection.css';

function TournamentCardEducatormysection({ tournament }) {
    const navigate = useNavigate();

    const goToTournamentBattles = () => {
        navigate(`/mysectionbattlespageeducator/${tournament.tournamentId}`);
    };

    function joinAddEducatortoaTournament(e) {
        e.stopPropagation();
        console.log(tournament.tournamentId);
        navigate(`/addeducatortoatournament/${tournament.tournamentId}`);
    }

    function joinCloseTournament(e) {
        e.stopPropagation();
        navigate(`/closetournament`);
    }

    function joinCreateBattle(e) {
        e.stopPropagation();
        navigate(`/createbattle/${tournament.tournamentId}`);
    }

    return (
        <div className="tournament-card" onClick={goToTournamentBattles}>
            <h3>{tournament.name}</h3>
            <p>{tournament.tournamentId}</p>
            <button className="join-button" onClick={joinAddEducatortoaTournament} >Add Educators to the Tournament
            </button>
            <button className="join-button" onClick={joinCloseTournament}>Close the Tournament</button>
            <button className="join-button" onClick={joinCreateBattle}>Create a Battle for the Tournament</button>

        </div>
    );
}

export default TournamentCardEducatormysection;
