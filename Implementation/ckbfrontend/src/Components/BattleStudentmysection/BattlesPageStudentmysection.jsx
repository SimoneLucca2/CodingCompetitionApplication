import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BattleCardStudentmysection from './BattleCardStudentmysection';
import './BattlesPageStudentmysection.css';
import {useParams} from "react-router-dom";
import API_URL from "../../config";
import BattleLeaderBoard from "../BattleLeaderBoard/BattleLeaderBoard";
import BattleCardEducator from "../BattleEducator/BattleCardEducator";

function BattlesPageStudentmysection({ match }) {
    const [selectedBattle, setSelectedBattle] = useState(null);
    const [loading, setLoading] = useState(true);
    const [battles, setBattles] = useState([]);

    const params = useParams();
    const tournament = params.tournamentId;
    const tournamentId = parseInt(tournament, 10);
    console.log(tournamentId);

    useEffect(() => {
        axios.get(`${API_URL}/battle/all/${tournamentId}`)
            .then(response => {
                setBattles(response.data);
                setLoading(false);
            })
            .catch(error => console.error('Error fetching battles:', error));
    }, []);

    const handleBattleSelectForLeaderboard = (battle) => {
        setSelectedBattle(battle);
    };

    const renderTournaments = () => {
        if (loading) return <p className="loading-message">Loading Battles...</p>;
        if (!battles || battles.length === 0) return <p className="no-tournaments-message">No battle created in this tournament.</p>;
        return battles.map(battle => (
            <BattleCardStudentmysection key={battle.id} battle={battle}
                                onLeaderboardSelect={() => handleBattleSelectForLeaderboard(battle)}/>
        ));
    }

    return (
        <div className="battles-page">
            <h1 className="page-title2">BATTLES OF THE TOURNAMENT</h1>
            <div className="battles-layout">
                <div className="battles-container">
                    {renderTournaments()}
                </div>
                <div className="battle-leaderboard">
                    {selectedBattle ? (
                        <BattleLeaderBoard battle={selectedBattle}/>
                    ) : (
                        <p className="select-battle-message">Select a battle to view the rankings.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default BattlesPageStudentmysection;
