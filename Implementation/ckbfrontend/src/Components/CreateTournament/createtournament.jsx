import React, { useState } from 'react';
import './createtournament.css'; // Importing the CSS file
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";
const CreateTournament = () => {
    const [name, setname] = useState('');
    const [description, setDescription] = useState('');
    const [registrationDeadline, setregistrationDeadline] = useState('');

    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const creatorId = oggettoSalvato.userId;

    const navigate = useNavigate();


    const handleSubmit = async (e) => {
        e.preventDefault();
        const tournamentData = {
            name: name,
            creatorId: creatorId,
            description: description,
            registrationDeadline: registrationDeadline,
            status: 'PREPARATION'
        };

        try {
            const response = await fetch(`${API_URL}/tournament`, {
                method: 'POST',
                body: JSON.stringify(tournamentData),
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                navigate('/errorpage');
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('Success:', data);
            navigate('/educatorprofile');
        } catch (error) {
            console.error('Error:', error);
        }

    };

    return (
        <div className="create-tournament">
            <form onSubmit={handleSubmit}>
                <label>
                    Tournament Name:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setname(e.target.value)}
                    />
                </label>
                <label>
                    Description:
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </label>
                <label>
                    Submission Deadline:
                    <input
                        type="datetime-local"
                        value={registrationDeadline}
                        onChange={(e) => setregistrationDeadline(e.target.value)}
                    />
                </label>
                <button type="submit">Create Tournament</button>
            </form>
        </div>
    );
};

export default CreateTournament;
