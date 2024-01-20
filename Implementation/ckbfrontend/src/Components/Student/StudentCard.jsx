import React from 'react';
import styles from './Student.module.css';
import PropTypes from 'prop-types';

function StudentCard(props) {
    return (
        <div className={styles.studentCard}>
            <p className={styles.studentParameter}>Name: {props.name}</p>
            <p className={styles.studentParameter}>Surname: {props.surname}</p>
        </div>
    );
}

StudentCard.propTypes = {
    name: PropTypes.string.isRequired,
    surname: PropTypes.string.isRequired,
};

StudentCard.defaultProps = {
    name: 'unknown',
    surname: 'unknown',
}

export default StudentCard;
