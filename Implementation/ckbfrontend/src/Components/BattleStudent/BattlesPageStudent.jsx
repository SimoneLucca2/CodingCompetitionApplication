import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BattleCardStudent from './BattleCardStudent';
import './BattlesPageStudent.css';
import {useParams} from "react-router-dom";
import API_URL from "../../config";

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

    const params = useParams();
    const tournament = params.tournamentId;
    const tournamentId = parseInt(tournament, 10);

    useEffect(() => {
        axios.get(`${API_URL}/battle/all/${tournamentId}`)
            .then(response => {
                setBattles(response.data);
            })
            .catch(error => console.error('Error fetching battles:', error));
    }, [tournamentId]);

    return (
        <div className="battles-page">
            <h1 className="page-title2">BATTLES OF THE TOURNAMENT</h1>
            <div className="battles-container">
            {battles.map(battle => (
                <BattleCardStudent key={battle.id} battle={battle}/>
            ))}
            </div>
        </div>
    );
}

export default BattlesPageStudent;
