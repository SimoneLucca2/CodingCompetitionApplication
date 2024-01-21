import React, { useState } from 'react';
import './addeducatortoatournament.css';
import {useNavigate} from "react-router-dom";

function AddEducatorToTournament() {
    const [requesterId, setRequesterId] = useState('');
    const [educatorId, setEducatorId] = useState('');
    const [tournamentId, setTournamentId] = useState('');

    const navigate = useNavigate();

    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }
    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Configura i dati che desideri inviare
        const dataToSend = {
            requesterID: requesterId,
            educatorID: educatorId,
            tournamentID: tournamentId
        };

        try {
            // Invia i dati al tuo endpoint API
            const response = await fetch('http://192.168.232.18:8080/tournament', {
                method: 'POST', // o 'PUT' se appropriato
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataToSend),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            {goTOuserprofile()};// Naviga alla userprofile
            const responseData = await response.json();
            console.log(responseData);
            alert('Educator Added to Tournament Successfully!');
        } catch (error) {
            console.error('There was an error!', error);
            {goTOerrorpage()};

        }
    };


    return (
        <div className="add-educator-container">
            <h2>Aggiungi un Educatore al Torneo</h2>
            <form onSubmit={handleSubmit} className="educator-form">
                <div className="form-group">
                    <label>ID Educator (Requester): </label>
                    <input
                        type="text"
                        value={requesterId}
                        onChange={(e) => setRequesterId(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label>ID Educator (To Add): </label>
                    <input
                        type="text"
                        value={educatorId}
                        onChange={(e) => setEducatorId(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label>ID Torneo: </label>
                    <input
                        type="text"
                        value={tournamentId}
                        onChange={(e) => setTournamentId(e.target.value)}
                    />
                </div>

                <button type="submit">Aggiungi Educator</button>
            </form>
        </div>
    );
}

export default AddEducatorToTournament;
