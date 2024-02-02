import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardStudentmysection from './TournamentCardStudentmysection';
import './TournamentsPageStudentmysection.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";
import TournamentLeaderboard from "../TournamentLeaderBoard/TournamentLeaderBoard";


function TournamentsPageStudentmysection() {
    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);
    const [selectedTournament, setSelectedTournament] = useState(null); // Torneo selezionato per la classifica
    const [tournaments, setTournaments] = useState([
        /*{
            tournamentId: 1,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5",
            status: "PREPARATION"
        },
        {
            tournamentId: 2,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10",
            status: "ACTIVE"
        },
        {
            tournamentId: 3,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5",
            status: "CLOSING"
        },
        {
            tournamentId: 4,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10",
            status: "CLOSED"

        },
        {
            tournamentId: 5,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            tournamentId: 6,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        },
        {
            tournamentId: 7,
            name: "Torneo di calcio",
            description: "Torneo di calcio a 5"
        },
        {
            tournamentId: 8,
            name: "Torneo di calcio2",
            description: "Torneo di calcio a 10"
        }*/
    ]);
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userID = oggettoSalvato?.userId;
    const userId = parseInt(userID, 10);

    const goToerrorpage = () => {
        navigate(`/errorpage`);
    };

    useEffect(() => {
        axios.get(`${API_URL}/tournament/enrolled/${userId}`)
            .then(response => {
                setTournaments(response.data);
            })
            .catch(error => {goToerrorpage()} );
    }, []);

    const handleTournamentSelectForLeaderboard = (tournament) => {
        setSelectedTournament(tournament);
    };

    return (
        <div className="tournaments-page">
            <h1 className="page-title">MY TOURNAMENTS</h1>
            <div className="tournaments-layout">
            <div className="tournaments-container">
                {tournaments.map(tournament => (
                    <TournamentCardStudentmysection key={tournament.tournamentId} tournament={tournament} onLeaderboardSelect={handleTournamentSelectForLeaderboard}/>
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

export default TournamentsPageStudentmysection;
