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

    function deleteSubscription(studentId, tournamentId) {        // Define the URL of your backend endpoint
        const url = `${API_URL}/tournament/student`;

        // Prepare the data to be sent
        fetch(url, {
            method: 'DELETE',
            headers: {
                // Aggiungi qui eventuali header richiesti, come Content-Type o token di autenticazione
                'Content-Type': 'application/json',
                // 'Authorization': 'Bearer tuoToken'
            },
            // Se necessario, includi il corpo della richiesta
            body: JSON.stringify({ tournamentId, studentId })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Errore HTTP: ${response.status}`);
                }
                return response.json(); // o response.text() se la risposta non è in JSON
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
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card
        deleteSubscription(userId, tournament.tournamentId);
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
            <button className="join-button" onClick={quitTournament}>QUIT THE TOURNAMENT</button>
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default TournamentCardStudentmysection;
