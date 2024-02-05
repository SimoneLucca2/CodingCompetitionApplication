import React from 'react';
import './NeedAuthentication.css';
import {useNavigate} from "react-router-dom";

const NeedAuthentication = () => {
    const number = 1;
    const navigate = useNavigate();
    const navigateToAuthentication = () => {
        navigate('/');
    };

    return (
        <div className="authentication-reminder">
            <p>Before using the services, you must authenticate.</p>
            <button onClick={navigateToAuthentication}>Return to Authentication Page</button>
        </div>
    );
};

export default NeedAuthentication;
