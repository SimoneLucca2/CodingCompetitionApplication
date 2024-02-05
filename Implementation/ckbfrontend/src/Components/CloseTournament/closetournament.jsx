import React, { useState } from 'react';
import './closetournament.css';

const CloseTournament = () => {
    const [educatorId, setEducatorId] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        console.log(`Educator ID: ${educatorId}`);
        console.log(`Message: ${message}`);

        setEducatorId('');
        setMessage('');
    };

    return (
        <div className="close-tournament-container">
            <h2>Close Tournament</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="educatorId">Educator ID:</label>
                    <input
                        type="text"
                        id="educatorId"
                        value={educatorId}
                        onChange={(e) => setEducatorId(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="message">Message:</label>
                    <textarea
                        id="message"
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                    ></textarea>
                </div>
                <button type="submit">Close Tournament</button>
            </form>
        </div>
    );
};

export default CloseTournament;
