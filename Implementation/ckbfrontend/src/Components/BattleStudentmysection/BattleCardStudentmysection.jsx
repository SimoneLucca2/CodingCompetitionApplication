import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudentmysection.css';
import API_URL from "../../config";

function BattleCardStudentmysection({ battle}) {
    const navigate = useNavigate();

    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato.userId;

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
            navigate(`/joinbattle`);

        } catch (error) {
            console.error("Error joining battle:", error);
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
            navigate(`/quitbattle`);

        } catch (error) {
            console.error("Error Quitting battle:", error);
            // Gestisci qui l'errore (ad es., mostrando un messaggio all'utente)
        }
    }


    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>Dettagli: {battle.details}</p>
            <button className="join-button" onClick={joinBattle}>Join the Battle</button>
            <button className="join-button" onClick={quitBattle}>Quit the Battle</button>

            {/* Altri dettagli della battaglia possono essere aggiunti qui */}
        </div>
    );
}

export default BattleCardStudentmysection;
