import React, { useState } from 'react';
import './createtournament.css'; // Importa il foglio di stile CSS

const TournamentCreationPage = () => {
    const [tournamentData, setTournamentData] = useState({
        name: '',
        registrationDeadline: '',
        description: '',
        educatorID: '',
        badges: []
    });

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://192.168.232.18:8080/tournament', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Aggiungi qui altri header se necessario, come l'autenticazione
                },
                body: JSON.stringify(tournamentData)
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Torneo creato:', data);
                // Gestisci la risposta del successo qui (es. reindirizzamento, messaggio di successo)
            } else {
                // Gestisci errori di risposta qui
                console.error('Errore nella creazione del torneo');
            }
        } catch (error) {
            // Gestisci errori di rete o altri errori qui
            console.error('Errore nella chiamata al backend:', error);
        }
    }


    const handleInputChange = (e) => {
        setTournamentData({ ...tournamentData, [e.target.name]: e.target.value });
    }

    const handleBadgeSelection = (badge) => {
        setTournamentData({
            ...tournamentData,
            badges: [...tournamentData.badges, badge]
        });
    }

    // Funzione per la selezione dei badges (da personalizzare)
    // ...

    return (
        <div className="tournament-creation-container">
            <h1>Crea il tuo Torneo</h1>
            <form onSubmit={handleSubmit} className="tournament-form">
                <div className="form-group">
                    <label>Nome del Torneo</label>
                    <input
                        type="text"
                        name="name"
                        placeholder="Inserisci il nome"
                        onChange={handleInputChange}
                    />
                </div>
                <div className="form-group">
                    <label>ID educatore</label>
                    <input
                        type="text"
                        name="name"
                        placeholder="Inserisci il tuo ID educatore"
                        onChange={handleInputChange}
                    />
                </div>
                <div className="form-group">
                    <label>Descrizione Opzionale del Torneo</label>
                    <textarea
                        name="description"
                        placeholder="Descrivi il torneo"
                        onChange={handleInputChange}
                    />
                </div>
                <div className="form-group">
                    <label>Badges</label>
                    {/* Qui inserire il codice per la selezione dei badges */}
                </div>
                <div className="form-group">
                    <label>Data Massima di Fine Registrazione</label>
                    <input
                        type="date"
                        name="registrationDeadline"
                        onChange={handleInputChange}
                    />
                </div>
                <button type="submit" className="submit-btn">Crea Torneo</button>
            </form>
        </div>
    );
}

export default TournamentCreationPage;

