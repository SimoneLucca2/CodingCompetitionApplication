import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BattleCardStudent from './BattleCardStudent';
import './BattlesPageStudent.css';
import {useParams} from "react-router-dom";

function BattlesPageStudent({ match }) {
    const [battles, setBattles] = useState([
        /*{
            id: 1,
            name: "Torneo di calcio",
            details: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            details: "Torneo di calcio a 10"
        }*/
    ]);
    let params;
    params = useParams();
    const tournamentId = params.tournamentId; // Assumendo che stai usando react-router

    useEffect(() => {
        axios.get(`/api/tournaments/${tournamentId}/battles`)
            .then(response => {
                setBattles(response.data);
            })
            .catch(error => console.error('Error fetching battles:', error));
    }, [tournamentId]);

    return (
        <div className="battles-page">
            <h1 className="page-title2">BATTLES OF THE TOURNAMENT: ${}</h1>
            <div className="battles-container">
            {battles.map(battle => (
                <BattleCardStudent key={battle.id} battle={battle}/>
            ))}
            </div>
        </div>
    );
}

export default BattlesPageStudent;
