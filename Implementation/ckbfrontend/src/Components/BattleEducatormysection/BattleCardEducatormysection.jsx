import React, {useEffect} from 'react';
import './BattleCardEducatormysection.css';
import API_URL from "../../config";
import {useNavigate} from "react-router-dom";

function BattleCardEducatormysection({ battle, onLeaderboardSelect}) {
    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);

    const battleId = battle.battleId;
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;
    const isEducator = userId === battle.creatorId;

    const handleLeaderboardClick = (e) => {
        e.stopPropagation();
        onLeaderboardSelect(battle);
    };

    function joinCloseTournament(e) {
        const url = `${API_URL}/battle/${battleId}`;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ battleId, userId })
        })
            .then(response => {
                if (!response.ok) {
                    alert('Error: ' + response.status);
                    throw new Error(`Errore HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Chiusura avvenuta con successo:', data);
                navigate(`/successpage`);
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
                navigate(`/errorpage`);
            });
    }


    const handleManualEvaluationClick = () => {
        navigate(`/manualevaluation/${battleId}`);
    };

    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Description: {battle.description}</p>
            <p>Registration Deadline:{battle.registrationDeadline}</p>
            <p>Submission Deadline:{battle.submissionDeadline}</p>
            <p>Status:{battle.status}</p>

            {battle.status === 'CONSOLIDATION' && isEducator && (
                <>
                    <button className="leaderboard-button-2" onClick={handleManualEvaluationClick}>
                        MANUAL EVALUATION
                    </button>
                    <button className="quit-button" onClick={joinCloseTournament}>CLOSE BATTLE</button>

                </>
            )}
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default BattleCardEducatormysection;
