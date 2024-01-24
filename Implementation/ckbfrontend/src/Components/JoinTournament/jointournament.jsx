import React, { useState } from 'react';
import './jointournament.css';
import {useNavigate} from "react-router-dom";

const JoinTournament = () => {
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
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ tournamentName: selectedTournament }),
        })
            .then(response => response.json())
            .then(data => {
                alert(`Successfully joined: ${selectedTournament}`);
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
                <h1>Join a Tournament</h1>
                <label htmlFor="tournament">Choose a Tournament:</label>
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
                <button type="submit">Join Tournament</button>
            </form>
        </div>
    );
}

export default JoinTournament;
