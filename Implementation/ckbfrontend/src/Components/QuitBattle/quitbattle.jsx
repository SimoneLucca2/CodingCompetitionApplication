import React, { useState } from 'react';
import './quitbattle.css';
import {useNavigate} from "react-router-dom";

const QuitBattle = () => {
    const [battleID, setBattleID] = useState('');
    const [studentID, setStudentId] = useState('');
    const [groupID, setGroupId] = useState('');

    const navigate = useNavigate();
    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }

    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Data to be sent to the backend
        const data = { battleID, studentID, groupID };

        try {
            const response = await fetch('http://192.168.232.18:8080/battle/student', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                {goTOerrorpage()}
            }

            const result = await response.json();
            console.log('Submission Successful:', result);
            // Handle the response data as needed
            {goTOuserprofile()};
        } catch (error) {
            console.error('Error during form submission:', error);
            // Handle errors, e.g., show error message to user
            {goTOerrorpage()};
        }
    };


    return (
        <div className="quit-battle-container">
            <div className="card">
                <div className="header">
                    <h1>Quit Battle</h1>
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="input-field">
                        <input
                            type="text"
                            value={battleID}
                            onChange={(e) => setBattleID(e.target.value)}
                            placeholder="Battle ID"
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="text"
                            value={studentID}
                            onChange={(e) => setStudentId(e.target.value)}
                            placeholder="Student ID"
                        />
                    </div>
                    <div className="input-field">
                        <input
                            type="text"
                            value={groupID}
                            onChange={(e) => setGroupId(e.target.value)}
                            placeholder="Group ID"
                        />
                    </div>
                    <div className="submit-button">
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default QuitBattle;
