import React, { useState } from 'react';
import './educatorprofile.css';
import sfondo from './sfondo.jpg';
const EducatorProfile = () => {
    const [profilePic, setProfilePic] = useState(sfondo);

    //const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    //const nickname = oggettoSalvato.nickname;
    const nickname = 'nickname';
    const handleProfilePicChange = (event) => {
        if (event.target.files && event.target.files[0]) {
            setProfilePic(URL.createObjectURL(event.target.files[0]));
        }
    };


    return (
        <div className="profile-container">
            <div className="profile-card">
                <div className="top-section">
                    <img src={profilePic} alt="Profile" className="profile-pic"/>
                    <input type="file" onChange={handleProfilePicChange} id="file-input" hidden/>
                    <label htmlFor="file-input" className="image-change-button">Change Photo</label>
                </div>
                <div className="bottom-section">
                    <h2 className="nickname">{nickname}</h2>
                    <p className="welcome-message">Welcome to your CKB HOME page!</p>
                    <div className="action-buttons">
                        <button onClick={() => window.location.href = '/tournamentspageeducator'}>View All Tournaments</button>
                        <button onClick={() => window.location.href = '/createtournament'}>Create a Tournament</button>
                        <button onClick={() => window.location.href = `/mysectiontournamentspageeducator`}>My Section</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EducatorProfile;
