import React from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import './errorpage.css';
import errorImage from './errorimage.png';

function ErrorPage({ message }) {
    const navigate = useNavigate();

    const containerVariants = {
        hidden: { opacity: 0, scale: 0.8 },
        visible: {
            opacity: 1,
            scale: 1,
            transition: { delay: 0.3, duration: 0.5 }
        },
    };

    const goBack = () => navigate(-2);

    return (
        <motion.div
            className="error-page"
            variants={containerVariants}
            initial="hidden"
            animate="visible"
        >
            <img src={errorImage} alt="Error" />
            <h1>Oops! Something went wrong...</h1>
            <p>{message || "We're sorry, but something went wrong. Please try again later."}</p>
            <button onClick={goBack}>Go Back</button>
        </motion.div>
    );
}

export default ErrorPage;
