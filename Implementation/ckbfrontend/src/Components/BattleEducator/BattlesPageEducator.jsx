import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BattleCardEducator from './BattleCardEducator';
import './BattlesPageEducator.css';
import {useNavigate, useParams} from "react-router-dom";
import API_URL from "../../config";
import BattleLeaderBoard from "../BattleLeaderBoard/BattleLeaderBoard";

function BattlesPageEducator({ match }) {
    const [selectedBattle, setSelectedBattle] = useState(null); // Torneo selezionato per la classifica

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
    const navigate = useNavigate();

    const handleBattleSelectForLeaderboard = (battle) => {
        setSelectedBattle(battle);
    };


    useEffect(() => {
        axios.get(`${API_URL}/battle/all/${tournamentId}`)
            .then(response => {
                setBattles(response.data);
            })
            .catch(error => navigate(`/errorpage`));
    }, [tournamentId]);

    return (
        <div className="battles-page">
            <h1 className="page-title2">BATTLES OF THE TOURNAMENT</h1>
            <div className="battles-layout">

                <div className="battles-container">
                    {battles.map(battle => (
                        <BattleCardEducator key={battle.id} battle={battle}
                                            onLeaderboardSelect={handleBattleSelectForLeaderboard()}/>
                    ))}
                </div>
                <div className="battles-leaderboard">
                    {selectedBattle ? (
                        <BattleLeaderBoard tournament={selectedBattle}/>
                    ) : (
                        <p className="select-battle-message">Select a battle to view the rankings.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default BattlesPageEducator;
