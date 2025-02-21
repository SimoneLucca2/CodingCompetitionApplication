import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './TournamentCardEducatormysection.css';
import API_URL from "../../config";

function TournamentCardEducatormysection({ tournament, onLeaderboardSelect }) {
    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);
    const tournamentId = tournament.tournamentId;
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;
    const educatorId = oggettoSalvato?.userId;
    const isEducator = userId === tournament.creatorId;


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

    const goToTournamentBattles = () => {
        navigate(`/mysectionbattlespageeducator/${tournament.tournamentId}`);
    };

    function joinAddEducatortoaTournament(e) {
        e.stopPropagation();
        console.log(tournament.tournamentId);
        navigate(`/addeducatortoatournament/${tournament.tournamentId}`);
    }

    function joinCloseTournament(e) {
        e.stopPropagation();

        const url = `${API_URL}/tournament/status`;

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ educatorId, tournamentId, status: 'CLOSING'})
        })
            .then(response => {
                if (!response.ok) {
                    alert('Error: ' + response.status);
                    throw new Error(`Errore HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Chiusura avvenuta con successo:', data);
                navigate(`/successpage`);
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
                navigate(`/errorpage`);
            });
    }

    function joinCreateBattle(e) {
        e.stopPropagation();
        navigate(`/createbattle/${tournament.tournamentId}`);
    }

    const handleLeaderboardClick = (e) => {
        e.stopPropagation();
        onLeaderboardSelect(tournament);
    };

    const showAddEducatorAndClose = ['PREPARATION', 'ACTIVE'].includes(tournament.status);
    const showCreateBattle = tournament.status === 'ACTIVE';

    return (
        <div className={`tournament-card ${isFlipped ? 'flipped' : ''} ${isVanished ? 'vanished' : ''}`}
             onClick={handleCardClick}>
            <h3>{tournament.name}</h3>
            <p>{tournament.description}</p>
            <p>Registration Deadline: {tournament.registrationDeadline}</p>
            <p>Status: {tournament.status}</p>
            {isEducator && showAddEducatorAndClose && (
                <>
                    <button className="join-button-1" onClick={joinAddEducatortoaTournament}>ADD EDUCATOR</button>
                    <button className="quit-button" onClick={joinCloseTournament}>CLOSE TOURNAMENT</button>
                </>
            )}
            {showCreateBattle && (
                <button className="join-button-2" onClick={joinCreateBattle}>CREATE BATTLE</button>
            )}
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
        </div>
    );
}

export default TournamentCardEducatormysection;
