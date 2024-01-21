import React from 'react';
import PropTypes from 'prop-types';
import TournamentCard from '../Tournament/TournamentCard';
import defaultTournamentImage from '../Tournament/Assets/tournamentPic.png';
import styles from './Styles/StudentProfile.module.css';

function StudentProfile({ user, tournaments }) {
    return (
        <div className={styles.profile}>
            <div className={styles.details}>
                <img src={user.imageUrl} alt={`${user.name} ${user.surname}`} className="user-image" />
                <h1>{user.name} {user.surname}</h1>
                <p>Nickname: {user.nickname}</p>
                <p>Email: {user.email}</p>
            </div>
            <div className={styles.studentTournaments}>
                <h2>Tournaments Enrolled</h2>
                {tournaments.map(tournament => (
                    <TournamentCard key={tournament.id} tournament={tournament} />
                ))}
            </div>
        </div>
    );
}

// Define PropTypes for StudentProfile
StudentProfile.propTypes = {
    user: PropTypes.shape({
        userId: PropTypes.number.isRequired,
        email: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        surname: PropTypes.string.isRequired,
        nickname: PropTypes.string,
        imageUrl: PropTypes.string.isRequired
    }).isRequired,
    tournaments: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired
    })).isRequired
};

StudentProfile.defaultProps = {
    user: {
        userId: 0,
        email: 'default@example.com',
        name: 'Default',
        surname: 'User',
        nickname: 'DefaultNickname',
        imageUrl: {defaultTournamentImage}
    },
    tournaments: []
};

export default StudentProfile;
