import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducator.css';

function TournamentCardEducator({ tournament }) {
    const navigate = useNavigate();

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    const goToTournamentBattles = () => {
        navigate(`/battlespageeducator`);
    };

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

    return (
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
        </div>
    );
}

export default TournamentCardEducator;
