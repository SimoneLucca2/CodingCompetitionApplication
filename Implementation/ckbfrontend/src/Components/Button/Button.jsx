import React from 'react';
import styles from './Styles/Button.module.css';

function Button({ onClick, buttonText }) {
    return (
        <button onClick={onClick} className={styles.button}>
            {buttonText || 'Click Me'}
        </button>
    );
}

export default Button;
