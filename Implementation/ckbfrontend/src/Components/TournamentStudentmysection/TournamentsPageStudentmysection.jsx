import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardStudentmysection from './TournamentCardStudentmysection';
import './TournamentsPageStudentmysection.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";
import TournamentLeaderboard from "../TournamentLeaderBoard/TournamentLeaderBoard";
import TournamentCardEducator from "../TournamentEducator/TournamentCardEducator";


function TournamentsPageStudentmysection() {
    const [selectedTournament, setSelectedTournament] = useState(null); // Torneo selezionato per la classifica
    const [tournaments, setTournaments] = useState([]);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
        axios.get(`${API_URL}/tournament/enrolled/${userId}`)
            .then(response => {
                setTournaments(response.data);
                setLoading(false);
            })
            .catch(error => {goToerrorpage()} );
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

    const renderTournaments = () => {
        if (loading) return <p className="loading-message">Loadind Tournaments...</p>;
        if (!tournaments || tournaments.length === 0) return <p className="no-tournaments-message">Al momento non ci sono tornei presenti.</p>;
        return tournaments.map(tournament => (
            <TournamentCardStudentmysection key={tournament.tournamentId} tournament={tournament}
                                    onLeaderboardSelect={handleTournamentSelectForLeaderboard}/>
        ));
    };

    return (
        <div className="tournaments-page">
            <h1 className="page-title">MY TOURNAMENTS</h1>
            <div className="tournaments-layout">
            <div className="tournaments-container">
                {renderTournaments()}
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
