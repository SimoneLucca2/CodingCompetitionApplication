import React from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom'; // Importa useNavigate
import './errorpage.css';
import errorImage from './errorimage.png';

function ErrorPage({ message }) {
    const navigate = useNavigate(); // Hook per la navigazione

    // Animations
    const containerVariants = {
        hidden: { opacity: 0, scale: 0.8 },
        visible: {
            opacity: 1,
            scale: 1,
            transition: { delay: 0.3, duration: 0.5 }
        },
    };

    // Funzione per tornare indietro
    const goBack = () => navigate(-1); // -1 per tornare alla pagina precedente

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
