import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardStudentmysection from './TournamentCardStudentmysection';
import './TournamentsPageStudentmysection.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";

function TournamentsPageStudentmysection() {
    const [tournaments, setTournaments] = useState([
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
        }
    ]);
    //const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    //const userId = oggettoSalvato.userId;
    const userId = 1;
    const navigate = useNavigate();

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


    return (
        <div className="tournaments-page">
            <h1 className="page-title">MY TOURNAMENTS</h1>
            <div className="tournaments-container">
                {tournaments.map(tournament => (
                    <TournamentCardStudentmysection key={tournament.tournamentId} tournament={tournament} />
                ))}
            </div>
        </div>
    );
}

export default TournamentsPageStudentmysection;
