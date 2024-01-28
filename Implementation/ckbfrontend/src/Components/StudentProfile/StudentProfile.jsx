import React, {useEffect, useState} from 'react';
import './StudentProfile.css';
import sfondo from './sfondo.jpg';
const StudentProfile = () => {
    const [profilePic, setProfilePic] = useState(sfondo);
    //const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    //const nickname = oggettoSalvato.nickname;
    const nickname = "Student";
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
                        <button onClick={() => window.location.href = '/tournamentspagestudent'}>View All Tournaments</button>
                        <button onClick={() => window.location.href = `/mysectiontournamentspagestudent`}>My Section</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StudentProfile;
