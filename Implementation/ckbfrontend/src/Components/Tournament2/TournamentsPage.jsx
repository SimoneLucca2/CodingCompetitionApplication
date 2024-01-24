import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCard from './TournamentCard';
import './TournamentsPage.css';
import {useNavigate} from "react-router-dom";

function TournamentsPage() {
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

    const navigate = useNavigate();

    const goToerrorpage = () => {
        navigate(`/errorpage`);
    };

    /*useEffect(() => {
        axios.get('/api/tournaments')
            .then(response => {
                setTournaments(response.data);
            })
            .catch(error => {goToerrorpage()} );
    }, []);*/


    return (
        <div className="tournaments-page">
            <h1 className="page-title">TOURNAMENTS</h1>
            <div className="tournaments-container">
                {tournaments.map(tournament => (
                    <TournamentCard key={tournament.id} tournament={tournament} />
                ))}
            </div>
        </div>
    );
}

export default TournamentsPage;
