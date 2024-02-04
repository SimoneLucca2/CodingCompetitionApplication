import React, {useEffect, useState} from 'react';
import './ManualEvaluation.css';
import {useNavigate, useParams} from "react-router-dom";
import API_URL from "../../config"; // Import CSS

function ManualEvaluation() {
    const navigate = useNavigate();

    const [showEvaluation, setShowEvaluation] = useState(false);
    const [score, setScore] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const [groups, setGroups] = useState([]); // Stato per l'elenco dei gruppi
    const [selectedGroup, setSelectedGroup] = useState('');

    const params = useParams();
    const battleId = params.battleId;
    const handleNoClick = () => {
        navigate(`/successpage`);
    };

    const fetchGroups = async () => {
        try {
            const response = await fetch(`${API_URL}/battle/groupup/all/${battleId}`);
            if (response.ok) {
                const data = await response.json();
                setGroups(data.ids);
                console.log('Gruppi recuperati:', data.ids);
                console.log('Gruppi:', groups);
            } else {
                console.error('Errore nel recupero dei gruppi');
            }
        } catch (error) {
            console.error('Errore nella richiesta:', error);
        }
    };

    useEffect(() => {
        fetchGroups(); // Recupera i gruppi all'avvio del componente
    }, [])

    const handleScoreSubmit = async () => {
        setSubmitting(true);
        try {
            const response = await fetch(`${API_URL}/battle/score/${battleId}/${selectedGroup}/${score}`, { // Includi l'ID del gruppo selezionato
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ score }),
            });
            if (response.ok) {
                navigate(`/successpage`);
                console.log('Score successfully submitted');
            } else {
                navigate(`/errorpage`);
                console.error('Error in submitting score');
            }
        } catch (error) {
            console.error('Error in request:', error);
            navigate(`/errorpage`);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="manual-evaluation">
            {showEvaluation ? (
                <div className="evaluation-form">
                    <div>
                        Select a group:
                        <select value={selectedGroup} onChange={e => setSelectedGroup(e.target.value)}>
                            <option value="">Select a group</option>
                            {groups.map(group => (
                                <option key={group.id} value={group.id}>{group.id}</option>
                            ))}
                        </select>
                    </div>
                    <label htmlFor="score">
                        Enter a score from 0 to 100:
                        <input
                            id="score"
                            type="number"
                            value={score}
                            onChange={e => setScore(e.target.value)}
                            min="0"
                            max="100"
                            className="score-input"
                        />
                    </label>
                    <button onClick={handleScoreSubmit} disabled={submitting || !selectedGroup} className="buttony">
                        Submit
                    </button>
                </div>
            ) : (
                <div className="evaluation-query">
                    Do you want to perform manual evaluation?
                    <button onClick={() => setShowEvaluation(true)} className="yes-button">Yes</button>
                    <button onClick={handleNoClick} className="no-button">No</button>
                </div>
            )}
        </div>
    );
}

export default ManualEvaluation;
