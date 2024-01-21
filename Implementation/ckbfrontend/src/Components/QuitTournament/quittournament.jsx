import React, { useState } from 'react';
import './quittournament.css';
import {useNavigate} from "react-router-dom";

const QuitTournament = () => {
    const [selectedTournament, setSelectedTournament] = useState('');

    const tournaments = ['Tournament 1', 'Tournament 2', 'Tournament 3']; // Replace with actual tournament data

    const navigate = useNavigate();
    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }

    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }
    const handleSubmit = (event) => {
        event.preventDefault();

        fetch('http://192.168.232.18:8080/tournament/student', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ tournamentName: selectedTournament }),
        })
            .then(response => response.json())
            .then(data => {
                alert(`Successfully quit: ${selectedTournament}`);
                // Handle success
                {goTOuserprofile()};// Naviga alla userprofile
            })
            .catch((error) => {
                console.error('Error:', error);
                // Handle errors here
                {goTOerrorpage()};// Naviga alla userprofile
            });
    };


    return (
        <div className="tournament-form-container">
            <form onSubmit={handleSubmit}>
                <h1>Quit a Tournament</h1>
                <label htmlFor="tournament">Select a Tournament to Quit:</label>
                <select
                    id="tournament"
                    value={selectedTournament}
                    onChange={(e) => setSelectedTournament(e.target.value)}
                    required
                >
                    <option value="">Select a Tournament</option>
                    {tournaments.map(tournament => (
                        <option key={tournament} value={tournament}>{tournament}</option>
                    ))}
                </select>
                <button type="submit">Quit Tournament</button>
            </form>
        </div>
    );
}

export default QuitTournament;
