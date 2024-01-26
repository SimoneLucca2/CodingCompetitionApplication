import React, { useState } from 'react';
import './createbattle.css';
import {useNavigate} from "react-router-dom";

function CreateBattle() {
    const [battleData, setBattleData] = useState({
        name: '',
        description: '',
        registrationDeadline: '',
        submissionDeadline: '',
        minMembers: 1,
        maxMembers: 10,
        educatorId: '',
        tournamentId: '',
        githubLink: ''
    });

    const navigate = useNavigate();
    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }

    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }

    const handleChange = (e) => {
        setBattleData({ ...battleData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const apiEndpoint = 'http://192.168.232.18:8080/battle'; // Replace with your actual API endpoint
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
            // Handle success (e.g., showing a success message, redirecting, etc.)
            {goTOuserprofile()};// Naviga alla userprofile
        } catch (error) {
            console.error("Error creating battle:", error);
            // Handle errors (e.g., showing an error message)
            {goTOerrorpage()};// Naviga alla userprofile
        }
    };


    return (
        <div className="create-battle">
            <form onSubmit={handleSubmit}>
                <h2 className="Title">Create a New Battle</h2>

                <label htmlFor="name">Battle Name:</label>
                <input type="text" id="name" name="name" value={battleData.name} onChange={handleChange} />

                <label htmlFor="description">Description:</label>
                <textarea id="description" name="description" value={battleData.description} onChange={handleChange} />

                <label htmlFor="registrationDeadline">Registration Deadline:</label>
                <input type="datetime-local" id="registrationDeadline" name="registrationDeadline" value={battleData.registrationDeadline} onChange={handleChange} />

                <label htmlFor="submissionDeadline">Submission Deadline:</label>
                <input type="datetime-local" id="submissionDeadline" name="submissionDeadline" value={battleData.submissionDeadline} onChange={handleChange} />

                <label htmlFor="minMembers">Minimum Members:</label>
                <input type="number" id="minMembers" name="minMembers" value={battleData.minMembers} onChange={handleChange} />

                <label htmlFor="maxMembers">Maximum Members:</label>
                <input type="number" id="maxMembers" name="maxMembers" value={battleData.maxMembers} onChange={handleChange} />

                <label htmlFor="educatorId">Educator ID:</label>
                <input type="text" id="educatorId" name="educatorId" value={battleData.educatorId} onChange={handleChange} />

                <label htmlFor="tournamentId">Tournament ID:</label>
                <input type="text" id="tournamentId" name="tournamentId" value={battleData.tournamentId} onChange={handleChange} />

                <label htmlFor="githubLink">GitHub Repository Link:</label>
                <input type="url" id="githubLink" name="githubLink" value={battleData.githubLink} onChange={handleChange} />

                <button type="submit">Create Battle</button>
            </form>
        </div>
    );
}

export default CreateBattle;
