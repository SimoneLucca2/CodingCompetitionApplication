import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducatormysection.css';

function TournamentCardEducatormysection({ tournament }) {
    const navigate = useNavigate();

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    const handleCardClick = () => {
        setIsFlipped(true);

        // Dopo un breve ritardo, applica l'effetto di svanimento e poi resetta
        setTimeout(() => {
            setIsVanished(true);

            setTimeout(() => {
                setIsFlipped(false);
                setIsVanished(false);
                goToTournamentBattles();
            }, 600); // Tempo per il reset dell'animazione
        }, 600); // Tempo prima che la carta svanisca
    };

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
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <button className="join-button" onClick={joinAddEducatortoaTournament}>ADD EDUCATOR</button>
            <button className="join-button" onClick={joinCloseTournament}>CLOSE TOURNAMENT</button>
            <button className="join-button" onClick={joinCreateBattle}>CREATE BATTLE</button>

        </div>
    );
}

export default TournamentCardEducatormysection;
