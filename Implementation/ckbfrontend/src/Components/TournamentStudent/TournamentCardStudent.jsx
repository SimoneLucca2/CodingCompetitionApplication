import React from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardStudent.css';
import API_URL from "../../config";
import axios from "axios";

function TournamentCardStudent({ tournament }) {
    const navigate = useNavigate();
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato.userId;

    function sendRequest(studentId, tournamentId) {
        // Define the URL of your backend endpoint
        const url = `${API_URL}/tournament/student`;

        // Prepare the data to be sent
        const data = {
            studentId: studentId,
            tournamentId: tournamentId
        };

        // Make the POST request
        axios.post(url, data)
            .then(response => {
                // Handle the response here
                console.log('Response received:', response.data);
                navigate(`/jointournament`);

            })
            .catch(error => {
                // Handle any errors here
                console.error('An error occurred:', error);
                navigate(`/errorpage`);
            });
    }

    const goToTournamentBattles = () => {
        navigate(`/battlespagestudent`);
    };

    const joinTournament = (e) => {
        e.stopPropagation(); // Impedisce al click sul bottone di attivare il click sulla card
        sendRequest(userId, tournament.tournamentId);
    };

    return (
        <div className="tournament-card" onClick={goToTournamentBattles}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <button className="join-button" onClick={joinTournament}>Join the Tournament</button>
        </div>
    );
}

export default TournamentCardStudent;
