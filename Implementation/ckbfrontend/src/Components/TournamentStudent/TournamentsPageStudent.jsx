import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardStudent from './TournamentCardStudent';
import './TournamentsPageStudent.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";

function TournamentsPageStudent() {
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
    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato.userId;
    const goToerrorpage = () => {
        navigate(`/errorpage`);
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
            <div className="tournaments-container">
                {tournaments.map(tournament => (
                    <TournamentCardStudent key={tournament.tournamentId} tournament={tournament} />
                ))}
            </div>
        </div>
    );
}

export default TournamentsPageStudent;
