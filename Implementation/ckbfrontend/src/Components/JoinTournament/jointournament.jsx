import React from 'react';
import './jointournament.css';

function SubscriptionSuccess() {
    return (
        <div className="subscription-success">
            <h1>Success!</h1>
            <p>You have successfully subscribed to the tournament.</p>
            <button onClick={() => window.location.href = '/tournamentspagestudent'}>Go Back</button>

        </div>
    );
}

export default SubscriptionSuccess;
