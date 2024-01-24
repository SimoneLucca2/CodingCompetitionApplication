import React from 'react';
import './errorpage.css';
import errorImage from './errorimmage.png'; // Assume you have an error-themed image

function ErrorPage({ message }) {
    return (
        <div className="error-page">
            <img src={errorImage} alt="Error" />
            <h1>Oops! Something went wrong...</h1>
            <p>{message || "We're sorry, but something went wrong. Please try again later."}</p>
            <button onClick={() => window.location.href = '/userprofile'}>Go Back Home</button>
        </div>
    );
}

export default ErrorPage;
