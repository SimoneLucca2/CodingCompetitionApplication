import React from 'react';
import './quitbattle.css';

function SubscriptionSuccess() {
    return (
        <div className="subscription-success">
            <h1>Success!</h1>
            <p>You have successfully quitted to the battle.</p>
            <button onClick={() => window.location.href = '/mysectiontournamentspagestudent'}>Go Back</button>

        </div>
    );
}

export default SubscriptionSuccess;

