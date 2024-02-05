import React, {useEffect, useState} from 'react';
import './addeducatortoatournament.css';
import {useNavigate, useParams} from "react-router-dom";
import API_URL from "../../config";
function AddEducatorToTournament() {
    const [educatorId, setEducatorId] = useState('');
    const [email, setEmail] = useState('');


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
    const params = useParams();
    const tournament = params.tournamentId;
    const tournamentId = parseInt(tournament, 10);

    const goTOuserprofile = () => {
        navigate('/successpage');
    }
    const goTOerrorpage = () => {
        navigate('/errorpage');
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response2 = await fetch(`${API_URL}/getId/${email}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response2.ok) {
                console.log("non va");
                alert("We are having problem, the email could be not valid. Try again ");
            }

            const responseData2 = await response2.json();
            console.log(responseData2);

            const dataToSend = {
                requesterId: userId,
                tournamentId: tournamentId,
                educatorId: responseData2.userId,
            };
            const response = await fetch(`${API_URL}/tournament/educator`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataToSend),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            {goTOuserprofile()};
            const responseData = await response.json();
            console.log(responseData);
        } catch (error) {
            console.error('There was an error!', error);
            alert("Error: "+ error.message);
            //{goTOerrorpage()};

        }
    };


    return (
        <div className="add-educator-container">
            <h2>ADD AN EDUCATOR TO THE TOURNAMENT</h2>
            <form onSubmit={handleSubmit} className="educator-form">
                <div className="form-group">
                    <label>EMAIL OF THE EDUCATOR TO ADD:</label>
                    <input
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <button type="submit">ADD EDUCATOR</button>
            </form>
        </div>
    );
}

export default AddEducatorToTournament;
