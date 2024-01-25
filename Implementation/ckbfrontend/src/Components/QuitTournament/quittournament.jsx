import React, { useState } from 'react';
import './quittournament.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";

const QuitTournament = () => {
    return (
        <div className="subscription-success">
            <h1>Success!</h1>
            <p>You have successfully unsubscribed from the tournament.</p>
            <button onClick={() => window.location.href = '/tournamentspagestudent'}>Go Back</button>

        </div>
    );
}

export default QuitTournament;
