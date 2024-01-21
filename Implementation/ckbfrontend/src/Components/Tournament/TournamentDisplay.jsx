// TournamentDisplay.js
import React from 'react';

const TournamentDisplay = ({ data }) => {
    if (!data) return null;

    return (
        <div>
            <p>Tournament ID: {data.tournamentId}</p>
            <p>Name: {data.name}</p>
            <p>Creator: {data.creatorId}</p>
            <p>Registration Deadline: {data.registrationDeadline}</p>
            <p>Status: {data.status}</p>
            {/* Render organizers and scores here */}
        </div>
    );
};

export default TournamentDisplay;
