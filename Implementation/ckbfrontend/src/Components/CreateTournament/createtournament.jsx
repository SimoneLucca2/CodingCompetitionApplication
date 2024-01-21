import React, { useState } from 'react';
import './createtournament.css';
import { useItemList } from "../../Context/ItemListContext";
import {useNavigate} from "react-router-dom";

const TournamentCreationPage = () => {
    const [tournamentData, setTournamentData] = useState({
        name: '',
        creatorId: '',
        registrationDeadline: '',
        status: 'PREPARATION',
    });
    const navigate = useNavigate();

    const ciao = {
        name: 'Torneo di prova',
        creatorId: 1,
        registrationDeadline: '2021-01-01',
        status: 'PREPARATION',
    }

    const { addItem } = useItemList();
    const { items } = useItemList();

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Ensuring creatorId is sent as a number
        const payload = {
            ...tournamentData,
            creatorId: parseInt(tournamentData.creatorId, 10) || 0,
        };

        try {
            addItem(ciao);
            console.log(items.map((tournament, index) => (tournament.name)));
            const response = await fetch('http://192.168.232.18:8080/tournament', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFREBnbWFpbC5jb20iLCJpYXQiOjE3MDU1OTExNzksImV4cCI6MTcwNjQ1NTE3OX0.JyPIdUmF9JTPc2mp-5z685T267BbhX9HO08yW8mkpHM', //
                },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Torneo creato:', data);
                addItem(data);
                navigate('/prova');
            } else {
                console.error('Errore nella creazione del torneo');
            }
        } catch (error) {
            console.error('Errore nella chiamata al backend:', error);
        }
    };

    const handleInputChange = (e) => {
        setTournamentData({ ...tournamentData, [e.target.name]: e.target.value });
    };

    const handleBadgeSelection = (badge) => {
        setTournamentData({
            ...tournamentData,
            badges: [...tournamentData.badges, badge],
        });
    }

    // Funzione per la selezione dei badges (da personalizzare)
    // ...

    return (
        <div className="tournament-creation-container">
            <h1>Crea il tuo Torneo</h1>
            <form onSubmit={handleSubmit} className="tournament-form">
                <div className="form-group">
                    <label>ID educatore</label>
                    <input
                        type="number"
                        name="creatorId"
                        placeholder="Inserisci il tuo ID educatore"
                        onChange={handleInputChange}
                    />
                </div>
                <div className="form-group">
                    <label>Nome Torneo</label>
                    <input
                        type="text"
                        name="name"
                        placeholder="Inserisci il nome torneo"
                        onChange={handleInputChange}
                    />
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
