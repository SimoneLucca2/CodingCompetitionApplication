import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudentmysection.css';
import API_URL from "../../config";

function BattleCardStudentmysection({ battle, onLeaderboardSelect}) {
    const [githubLink, setGithubLink] = useState('');

    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);

    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;

    async function joinBattle(e) {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card

        // Preparazione del corpo della richiesta
        const requestBody = {
            studentId: userId,
            battleId: battle.battleId // Assumendo che ogni battaglia abbia un campo 'id'
        };
        console.log(requestBody);

        try {
            const response = await fetch(`${API_URL}/battle/student`, { // Sostituisci '/api/joinbattle' con il tuo endpoint specifico
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
                navigate(`/errorpage`);
            }

            // Gestisci qui la risposta in caso di successo
            console.log("Joined battle successfully");
            navigate(`/successpage`);

        } catch (error) {
            console.error("Error joining battle:", error);
            navigate(`/errorpage`);
            // Gestisci qui l'errore (ad es., mostrando un messaggio all'utente)
        }
    }

    async function quitBattle(e) {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card

        // Preparazione del corpo della richiesta
        const requestBody = {
            studentId: userId,
            battleId: battle.battleId // Assumendo che ogni battaglia abbia un campo 'id'
        };
        console.log(requestBody);

        try {
            const response = await fetch(`${API_URL}/battle/student`, { // Sostituisci '/api/joinbattle' con il tuo endpoint specifico
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
                navigate(`/errorpage`);
            }

            // Gestisci qui la risposta in caso di successo
            console.log("Quitted battle successfully");
            navigate(`/successpage`);

        } catch (error) {
            console.error("Error Quitting battle:", error);
            navigate(`/errorpage`);
            // Gestisci qui l'errore (ad es., mostrando un messaggio all'utente)
        }
    }

    const handleLeaderboardClick = (e) => {
        e.stopPropagation(); // Previene il click sull'intera carta
        onLeaderboardSelect(battle);
    };

    const showGithubLinkInput = battle.status === 'PRE_BATTLE' || battle.status === 'BATTLE' || battle.status === 'CONSOLIDATION';

    async function sendGithubLink(e) {
        e.stopPropagation(); // Previene la propagazione dell'evento

        const requestBody = {
            studentId: userId,
            battleId: battle.battleId,
            clonedRepositoryLink: githubLink,
        };

        try {
            const response = await fetch(`${API_URL}/battle/group/repo`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            // Qui gestisci la risposta positiva, come un messaggio all'utente
            alert("GitHub link sent successfully!");

        } catch (error) {
            console.error("Error sending GitHub link:", error);
            // Gestisci qui l'errore
            alert("Failed to send GitHub link.");
        }
    }

    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>DESCRIPTION: {battle.description}</p>
            <p>REGISTRATION DEADLINE: {battle.registrationDeadline}</p>
            <p>SUBMISSION DEADLINE: {battle.submissionDeadline}</p>
            <p>Status: {battle.status}</p>
            <p>Link Repo: {battle.repoLink}</p>


            <button className="join-button-3" onClick={joinBattle}>Join the Battle</button>
            <button className="join-button-4" onClick={quitBattle}>Quit the Battle</button>
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
            {showGithubLinkInput && (
                <div className="github-link-container">
                    <input
                        type="text"
                        placeholder="GitHub Repo Link"
                        value={githubLink}
                        onChange={(e) => setGithubLink(e.target.value)}
                        className="github-link-input"
                    />
                    <button onClick={sendGithubLink} className="send-github-link-button">Send</button>
                </div>
            )}

        </div>
    );
}

export default BattleCardStudentmysection;
