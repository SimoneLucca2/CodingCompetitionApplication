import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './BattleCardStudentmysection.css';
import API_URL from "../../config";

function BattleCardStudentmysection({ battle, onLeaderboardSelect}) {
    const [githubLink, setGithubLink] = useState('');

    const navigate = useNavigate();
    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }
    }, [navigate]);

    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;

    async function joinBattle(e) {
        e.stopPropagation();

        const requestBody = {
            studentId: userId,
            battleId: battle.battleId
        };
        console.log(requestBody);

        try {
            const response = await fetch(`${API_URL}/battle/student`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            console.log("Joined battle successfully");
            navigate(`/successpage`);

        } catch (error) {
            alert("Error joining battle:", error);
        }
    }

    async function quitBattle(e) {
        e.stopPropagation();

        const requestBody = {
            studentId: userId,
            battleId: battle.battleId
        };
        console.log(requestBody);

        try {
            const response = await fetch(`${API_URL}/battle/student`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            console.log("Quitted battle successfully");
            navigate(`/successpage`);

        } catch (error) {
            alert("Error Quitting battle:", error);
        }
    }

    const handleLeaderboardClick = (e) => {
        e.stopPropagation();
        onLeaderboardSelect(battle);
    };

    const showGithubLinkInput = battle.status === 'PRE_BATTLE' || battle.status === 'BATTLE' ;
    async function sendGithubLink(e) {
        e.stopPropagation();

        const requestBody = {
            studentId: userId,
            battleId: battle.battleId,
            clonedRepositoryLink: githubLink,
        };

        try {
            const response = await fetch(`${API_URL}/battle/group/repo`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            alert("GitHub link sent successfully!");

        } catch (error) {
            console.error("Error sending GitHub link:", error);
            alert("Failed to send GitHub link.");
        }
    }

    function handleGroupClick() {
        navigate(`/participategroup/${battle.battleId}/${battle.status}`);
    }

    return (
        <div className="battle-card">
            <h3>{battle.name}</h3>
            <p>DESCRIPTION: {battle.description}</p>
            <p>REGISTRATION DEADLINE: {battle.registrationDeadline}</p>
            <p>SUBMISSION DEADLINE: {battle.submissionDeadline}</p>
            <p>Status: {battle.status}</p>
            <p>Link Repo: {battle.repoLink}</p>
            {battle.status === 'PRE_BATTLE' && (
                <button className="join-button-3" onClick={joinBattle}>Join the Battle</button>
            )}
            {(battle.status === 'PRE_BATTLE' || battle.status === 'BATTLE') && (
                <button className="join-button-4" onClick={quitBattle}>Quit the Battle</button>
            )}
            <button className="leaderboard-button" onClick={handleLeaderboardClick}>
                View Leaderboard
            </button>
            <button className="leaderboard-button" onClick={handleGroupClick}>
                    View Group
                </button>
            {showGithubLinkInput && (
                <div className="github-link-container">
                    <input
                        type="text"
                        placeholder="GitHub Repo Link"
                        value={githubLink}
                        onChange={(e) => setGithubLink(e.target.value)}
                        className="github-link-input"
                    />
                    <button onClick={sendGithubLink} className="send-github-link-button">Send</button>
                </div>
            )}

        </div>
    );
}

export default BattleCardStudentmysection;
