import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardStudent.css';
import API_URL from "../../config";
import axios from "axios";

function TournamentCardStudent({ tournament }) {
    const navigate = useNavigate();
    //const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    //const userId = oggettoSalvato.userId;
    const userId = 1;

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    const handleCardClick = () => {
        setIsFlipped(true);

        // Dopo un breve ritardo, applica l'effetto di svanimento e poi resetta
        setTimeout(() => {
            setIsVanished(true);

            setTimeout(() => {
                setIsFlipped(false);
                setIsVanished(false);
                goToTournamentBattles();
            }, 600); // Tempo per il reset dell'animazione
        }, 600); // Tempo prima che la carta svanisca
    };

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
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <button className="join-button-3" onClick={joinTournament}>JOIN THE TOURNAMENT</button>
        </div>
    );
}

export default TournamentCardStudent;
