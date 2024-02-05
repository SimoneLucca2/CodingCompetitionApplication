import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducator.css';

function TournamentCardEducator({ tournament, onLeaderboardSelect }) {
    const navigate = useNavigate();

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    const goToTournamentBattles = () => {
        navigate(`/battlespageeducator/${tournament.tournamentId}`);
    };

    const handleCardClick = () => {
        setIsFlipped(true);

        setTimeout(() => {
            setIsVanished(true);

            setTimeout(() => {
                setIsFlipped(false);
                setIsVanished(false);
                goToTournamentBattles();
            }, 600);
        }, 600);
    };


    const handleLeaderboardClick = (e) => {
        e.stopPropagation(); // Previene il click sull'intera carta
        onLeaderboardSelect(tournament);
    };

    return (
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <p>Status:{tournament.status}</p>
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default TournamentCardEducator;
