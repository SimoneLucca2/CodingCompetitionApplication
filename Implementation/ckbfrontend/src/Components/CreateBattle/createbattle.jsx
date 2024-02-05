import React, {useEffect, useState} from 'react';
import './createbattle.css';
import {useNavigate, useParams} from "react-router-dom";
import API_URL from "../../config";

function CreateBattle() {
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
    const params = useParams();
    const tournament = params.tournamentId;
    const tournamentId = parseInt(tournament, 10);
    const [battleData, setBattleData] = useState({
        name: '',
        description: '',
        registrationDeadline: '',
        submissionDeadline: '',
        minGroupSize: 1,
        maxGroupSize: 10,
        creatorId: userId,
        tournamentId: tournamentId,
    });

    const goTOuserprofile = () => {
        navigate('/successpage');
    }

    const goTOerrorpage = () => {
        navigate('/errorpage');
    }

    const handleChange = (e) => {
        setBattleData({ ...battleData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const apiEndpoint = `${API_URL}/battle`;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(battleData)
        };

        try {
            const response = await fetch(apiEndpoint, options);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
                {goTOerrorpage()};
            }
            const result = await response.json();
            console.log("Battle created successfully:", result);
            {goTOuserprofile()};
        } catch (error) {
            console.error("Error creating battle:", error);
            //{goTOerrorpage()};
            alert("Error creating battle: it could be the battle already exists or for network problem, try again. ", error);
        }
    };


    return (
        <div className="create-battle">
            <form onSubmit={handleSubmit}>
                <h2 className="Title">CREATE NEW BATTLE</h2>

                <label htmlFor="name">BATTLE NAME:</label>
                <input type="text" id="name" name="name" value={battleData.name} onChange={handleChange} />

                <label htmlFor="description">DESCRIPTION:</label>
                <textarea id="description" name="description" value={battleData.description} onChange={handleChange} />

                <label htmlFor="registrationDeadline">REGISTRATION DEADLINE:</label>
                <input type="datetime-local" id="registrationDeadline" name="registrationDeadline" value={battleData.registrationDeadline} onChange={handleChange} />

                <label htmlFor="submissionDeadline">SUBMISSION DEADLINE:</label>
                <input type="datetime-local" id="submissionDeadline" name="submissionDeadline" value={battleData.submissionDeadline} onChange={handleChange} />

                <label htmlFor="minMembers">MINIMUM MEMBERS:</label>
                <input type="number" id="minGroupSize" name="minGroupSize" value={battleData.minGroupSize} onChange={handleChange} />

                <label htmlFor="maxMembers">MAXIMUM MEMBERS</label>
                <input type="number" id="maxGroupSize" name="maxGroupSize" value={battleData.maxGroupSize} onChange={handleChange} />

                <button type="submit">CREATE BATTLE</button>
            </form>
        </div>
    );
}

export default CreateBattle;
