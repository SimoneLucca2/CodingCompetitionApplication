import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardStudentmysection.css';
import API_URL from "../../config";
function TournamentCardStudentmysection({ tournament, onLeaderboardSelect }) {
    const navigate = useNavigate();
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    if (!oggettoSalvato) {
        navigate(`/needauthentication`, { replace: true });
    }
    const userId = oggettoSalvato?.userId;

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    function deleteSubscription(studentId, tournamentId) {
        const url = `${API_URL}/tournament/student`;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ tournamentId, studentId })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Errore HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Cancellazione avvenuta con successo:', data);
                navigate(`/successpage`);
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
                navigate(`/errorpage`);
            });
    }

    const goToTournamentBattles = () => {
        navigate(`/mysectiontbattlespagestudent/${tournament.tournamentId}`);
    };



    const quitTournament = (e) => {
        e.stopPropagation();
        deleteSubscription(userId, tournament.tournamentId);
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
        e.stopPropagation();
        onLeaderboardSelect(tournament);
    };

    return (
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <p>Status:{tournament.status}</p>
            <button className="join-button" onClick={quitTournament}>QUIT THE TOURNAMENT</button>
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default TournamentCardStudentmysection;
