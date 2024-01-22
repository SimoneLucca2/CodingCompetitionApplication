import React, { useState, useEffect } from 'react';
import './joinbattle.css';
import {useNavigate} from "react-router-dom";

const JoinBattle = () => {
    const [battles, setBattles] = useState([]);
    const [selectedBattle, setSelectedBattle] = useState('');
    const [studentId, setStudentId] = useState('');
    const [isLoading, setIsLoading] = useState(true);

    const navigate = useNavigate();
    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }

    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }

    useEffect(() => {
        // Simulate fetching data with a delay
        setTimeout(() => {
            // Replace with actual data fetching logic
            setBattles(['Dragon War', 'Wizard Duel', 'Knight Tournament']);
            setIsLoading(false);
        }, 2000);
    }, []);

    const handleBattleChange = (event) => {
        setSelectedBattle(event.target.value);
    };

    const handleStudentIdChange = (event) => {
        setStudentId(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Check if a battle is selected and a student ID is entered
        if (!selectedBattle || !studentId) {
            alert('Please select a battle and enter your student ID.');
            return;
        }

        try {
            const response = await fetch('http://192.168.232.18:8080/battle/student', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ selectedBattle, studentId }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            console.log(result);

            // Handle success - you can redirect the user or show a success message
            {goTOuserprofile()};// Naviga alla userprofile
        } catch (error) {
            console.error('Error joining battle:', error);
            // Handle errors - show user a message, etc.
            {goTOerrorpage()};// Naviga alla userprofile
        }
    };


    if (isLoading) {
        return <div className="loading">Loading battles...</div>;
    }

    return (
        <div className="joinBattleContainer">
            <h1>Join a Battle</h1>
            <div className="joinBattle">
                <form onSubmit={handleSubmit}>
                    <div className="formGroup">
                        <label htmlFor="battleSelect">Select Battle:</label>
                        <select id="battleSelect" value={selectedBattle} onChange={handleBattleChange}>
                            <option value="">Select a Battle</option>
                            {battles.map((battle, index) => (
                                <option key={index} value={battle}>{battle}</option>
                            ))}
                        </select>
                    </div>
                    <div className="formGroup">
                        <label htmlFor="studentIdInput">Student ID:</label>
                        <input id="studentIdInput" type="text" value={studentId} onChange={handleStudentIdChange} />
                    </div>
                    <button type="submit" className="joinButton">Join Battle</button>
                </form>
            </div>
        </div>
    );
};

export default JoinBattle;
