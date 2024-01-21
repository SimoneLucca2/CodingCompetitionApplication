import React from 'react';
import PropTypes from 'prop-types';
import defaultTournamentImage from './styles/TournamentCard.css';

function TournamentCard({ tournament }) {
    return (
        <div className="tournament-card">
            <img className="tournament-card-image" src={tournament.image} />
            <h2 className="tournament-card-title">{tournament.name}</h2>
            <p className="tournament-card-text">{tournament.description}</p>
        </div>
    );
}

// Define the expected PropTypes
TournamentCard.propTypes = {
    tournament: PropTypes.shape({
        image: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
    }).isRequired
};

// Define default props in case they aren't passed
TournamentCard.defaultProps = {
    tournament: {
        image: {defaultTournamentImage},
        name: 'Unknown name',
        description: 'No description provided',
    }
};

export default TournamentCard;
