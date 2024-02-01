import React from 'react';
import './BattleCardEducatormysection.css';
import API_URL from "../../config";
import {useNavigate} from "react-router-dom";

function BattleCardEducatormysection({ battle, onLeaderboardSelect}) {
    const navigate = useNavigate();

    const battleId = battle.battleId;
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato.userId;
    const handleLeaderboardClick = (e) => {
        e.stopPropagation(); // Previene il click sull'intera carta
        onLeaderboardSelect(battle);
    };

    function joinCloseTournament(e) {
        const url = `${API_URL}/battle/${battleId}`;

        // Prepare the data to be sent
        fetch(url, {
            method: 'DELETE',
            headers: {
                // Aggiungi qui eventuali header richiesti, come Content-Type o token di autenticazione
                'Content-Type': 'application/json',
                // 'Authorization': 'Bearer tuoToken'
            },
            // Se necessario, includi il corpo della richiesta
            body: JSON.stringify({ battleId, userId })
        })
            .then(response => {
                if (!response.ok) {
                    alert('Error: ' + response.status);
                    throw new Error(`Errore HTTP: ${response.status}`);
                }
                return response.json(); // o response.text() se la risposta non è in JSON
            })
            .then(data => {
                console.log('Chiusura avvenuta con successo:', data);
                navigate('/manualevaluation/battleId');
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
                navigate(`/errorpage`);
            });
    }


    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Description: {battle.description}</p>
            <p>Registration Deadline:{battle.registrationDeadline}</p>
            <p>Registration Deadline:{battle.submissionDeadline}</p>
            <p>Status:{battle.status}</p>

            {battle.status === 'CONSOLDIDATION' && (
                <button className="quit-button" onClick={joinCloseTournament}>CLOSE BATTLE</button>
            )}
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default BattleCardEducatormysection;
