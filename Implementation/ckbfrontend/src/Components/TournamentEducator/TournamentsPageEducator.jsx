import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TournamentCardEducator from './TournamentCardEducator';
import './TournamentsPageEducator.css';
import {useNavigate} from "react-router-dom";
import API_URL from "../../config";

function TournamentsPageEducator() {
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

    useEffect(() => {
        axios.get(`${API_URL}/tournament/all`)
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
                    <TournamentCardEducator key={tournament.tournamentId} tournament={tournament} />
                ))}
            </div>
        </div>
    );
}

export default TournamentsPageEducator;