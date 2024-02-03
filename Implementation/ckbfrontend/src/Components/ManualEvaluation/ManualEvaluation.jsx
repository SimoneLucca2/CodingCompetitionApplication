import React, { useState } from 'react';
import './ManualEvaluation.css';
import {useNavigate, useParams} from "react-router-dom";
import API_URL from "../../config"; // Import CSS

function ManualEvaluation() {
    const navigate = useNavigate();

    const [showEvaluation, setShowEvaluation] = useState(false);
    const [score, setScore] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const params = useParams();
    const battleId = params.battleId;
    const handleNoClick = () => {
        navigate(`/successpage`);
    };

    const handleScoreSubmit = async () => {
        setSubmitting(true);
        try {
            const response = await fetch(`${API_URL}/battle/${battleId}/all`, {
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
                    <button onClick={handleScoreSubmit} disabled={submitting} className="buttony">
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
