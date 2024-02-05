import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardStudent.css';
import API_URL from "../../config";
import axios from "axios";

function TournamentCardStudent({ tournament, onLeaderboardSelect }) {
    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;

    const [isFlipped, setIsFlipped] = useState(false);
    const [isVanished, setIsVanished] = useState(false);

    const handleCardClick = () => {
        setIsFlipped(true);

        setTimeout(() => {
            setIsVanished(true);

            setTimeout(() => {
                setIsFlipped(false);
                setIsVanished(false);
                goToTournamentBattles();
            }, 600);
        }, 600);
    };

    function sendRequest(studentId, tournamentId) {
        const url = `${API_URL}/tournament/student`;

        const data = {
            studentId: studentId,
            tournamentId: tournamentId
        };

        axios.post(url, data)
            .then(response => {
                console.log('Response received:', response.data);
                navigate(`/successpage`);

            })
            .catch(error => {
                console.error('An error occurred:', error);
                navigate(`/errorpage`);
            });
    }

    const goToTournamentBattles = () => {
        navigate(`/battlespagestudent/${tournament.tournamentId}`);
    };

    const joinTournament = (e) => {
        e.stopPropagation();
        sendRequest(userId, tournament.tournamentId);
    };

    const handleLeaderboardClick = (e) => {
        e.stopPropagation();
        onLeaderboardSelect(tournament);
    };

    return (
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline:{tournament.registrationDeadline}</p>
            <p>Status:{tournament.status}</p>
            <button className="join-button-3" onClick={joinTournament}>JOIN THE TOURNAMENT</button>
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default TournamentCardStudent;
