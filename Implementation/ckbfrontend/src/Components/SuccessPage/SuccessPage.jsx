// SuccessPage.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaCheckCircle } from 'react-icons/fa';
import { useSpring, animated } from 'react-spring';
import './SuccessPage.css';

const SuccessPage = () => {
    const navigate = useNavigate();
    const fade = useSpring({ from: { opacity: 0 }, opacity: 1, delay: 300 });
    const buttonAnimation = useSpring({ from: { scale: 0.8 }, to: { scale: 1 }, loop: { reverse: true } });

    return (
        <div className="success-container">
            <animated.div style={fade} className="success-card">
                <animated.div style={fade}>
                    <FaCheckCircle className="success-icon"/>
                    <h1>Action Completed!</h1>
                    <p>Your process has been successfully completed.</p>
                </animated.div>
                <animated.button style={buttonAnimation} onClick={() => navigate(-2)}>Go Back</animated.button>
            </animated.div>
        </div>
    );
}

export default SuccessPage;
