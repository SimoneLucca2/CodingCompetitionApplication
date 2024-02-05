import React, {useEffect, useState} from 'react';
import './createtournament.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";
const CreateTournament = () => {
    const [name, setname] = useState('');
    const [description, setDescription] = useState('');
    const [registrationDeadline, setregistrationDeadline] = useState('');
    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const creatorId = oggettoSalvato?.userId;


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
                //navigate('/errorpage');
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('Success:', data);
            navigate('/successpage');
        } catch (error) {
            alert('Error: the tournament could already exists or it could be network trouble', );
        }
    };

    return (
        <div className="create-tournament">
            <form onSubmit={handleSubmit}>
                <label>
                    TOURNAMENT NAME:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setname(e.target.value)}
                    />
                </label>
                <label>
                    DESCRIPTION:
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </label>
                <label>
                    SUBMISSION DEADLINE:
                    <input
                        type="datetime-local"
                        value={registrationDeadline}
                        onChange={(e) => setregistrationDeadline(e.target.value)}
                    />
                </label>
                <button type="submit">CREATE TOURNAMENT</button>
            </form>
        </div>
    );
};

export default CreateTournament;
