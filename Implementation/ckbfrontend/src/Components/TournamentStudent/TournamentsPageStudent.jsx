import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardStudent from './TournamentCardStudent';
import './TournamentsPageStudent.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";
import TournamentLeaderboard from "../TournamentLeaderBoard/TournamentLeaderBoard";

function TournamentsPageStudent() {
    const [selectedTournament, setSelectedTournament] = useState(null); // Torneo selezionato per la classifica
    const [tournaments, setTournaments] = useState([
       /*{
            id: 1,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        },
        {
            id: 1,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        },
        {
            id: 1,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        },
        {
            id: 1,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        }*/
    ]);

    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userID = oggettoSalvato?.userId;
    const userId = parseInt(userID, 10);
    const goToerrorpage = () => {
        navigate(`/errorpage`);
    };

    const handleTournamentSelectForLeaderboard = (tournament) => {
        setSelectedTournament(tournament);
    };

    useEffect(() => {
        axios.get(`${API_URL}/tournament/notEnrolled/${userId}`)
            .then(response => {
                setTournaments(response.data);
            })
            .catch(error => {goToerrorpage()} );
    }, []);


    return (
        <div className="tournaments-page">
            <h1 className="page-title">ALL TOURNAMENTS</h1>
            <div className="tournaments-layout">
            <div className="tournaments-container">
                {tournaments.map(tournament => (
                    <TournamentCardStudent key={tournament.tournamentId} tournament={tournament} onLeaderboardSelect={handleTournamentSelectForLeaderboard}/>
                ))}
            </div>
            <div className="tournament-leaderboard">
                {selectedTournament ? (
                    <TournamentLeaderboard tournament={selectedTournament}/>
                ) : (
                    <p className="select-tournament-message">Select a tournament to view the rankings.</p>
                )}
            </div>
            </div>
        </div>
    );
}

export default TournamentsPageStudent;
