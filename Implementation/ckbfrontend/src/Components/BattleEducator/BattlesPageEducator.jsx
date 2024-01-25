import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BattleCardEducator from './BattleCardEducator';
import './BattlesPageEducator.css';

function BattlesPageEducator({ match }) {
    const [battles, setBattles] = useState([
        {
            id: 1,
            name: "Torneo di calcio",
            details: "Torneo di calcio a 5"
        },
        {
            id: 2,
            name: "Torneo di calcio2",
            details: "Torneo di calcio a 10"
        }
    ]);
    //const tournamentId = match.params.tournamentId; // Assumendo che stai usando react-router

    /*useEffect(() => {
        axios.get(`/api/tournaments/${tournamentId}/battles`)
            .then(response => {
                setBattles(response.data);
            })
            .catch(error => console.error('Error fetching battles:', error));
    }, [tournamentId]);*/

    return (
        <div className="battles-page">
            <h1 className="page-title2">BATTLES OF THE TOURNAMENT: ${}</h1>
            <div className="battles-container">
            {battles.map(battle => (
                <BattleCardEducator key={battle.id} battle={battle}/>
            ))}
            </div>
        </div>
    );
}

export default BattlesPageEducator;
