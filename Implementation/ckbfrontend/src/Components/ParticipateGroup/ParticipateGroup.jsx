import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ParticipateGroup.css';
import API_URL from "../../config";
import {useNavigate, useParams} from "react-router-dom";

const GroupComponent = () => {
    const [groupId, setGroupId] = useState('');
    const [userEmails, setUserEmails] = useState([]);
    const [newGroupId, setNewGroupId] = useState('');
    const [receiverId, setreceiverId] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, {replace: true});
            return;
        }
    }, [navigate]);

    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;

    const params = useParams();
    const battle = params.battleId;
    const battleId = parseInt(battle, 10);

    // Function to join the group
    useEffect(() => {
        const fetchGroupId = async () => {
            try {
                // Costruisce l'URL con i parametri di query
                const url = `${API_URL}/battle/group/id/${battleId}/${userId}`;
                const response = await axios.get(url);
                setGroupId(response.data.groupId);
            } catch (error) {
                console.error("Error fetching group ID", error);
                alert("Error fetching group ID");
            }
        };

        const fetchUserEmails = async () => {
            try {
                const url = `${API_URL}/battle/group/students/${battleId}`;
                const response = await axios.get(url);
                const studentIds = response.data;

                console.log(studentIds);

                if (Array.isArray(studentIds)) {
                    console.log("studentIds è un array");
                    const emails = await Promise.all(studentIds.map(async (id) => {
                        const url = `${API_URL}/user/email/${encodeURIComponent(id)}`;
                        const {data: userData} = await axios.get(url);
                        console.log(userData);
                        return userData;

                    }));
                    setUserEmails(emails);
                } else {
                    console.error('studentIds non è un array:', studentIds);
                    // Gestisci il caso in cui studentIds non è un array
                }
            } catch (error) {
                console.error("Error fetching user emails", error);
                alert("Error fetching user emails");
            }
        };

        fetchGroupId();
        fetchUserEmails();

    }, [userId, battleId]);


    // Function to leave the group
    async function leaveGroup() {

        const requestBody = {
            studentId: userId,
            groupId: groupId
        };
        console.log(requestBody);

        try {
            const response = await fetch(`${API_URL}/battle/group`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            setGroupId('');
            console.log("Quitted group successfully");
            navigate(`/successpage`);

        } catch (error) {
            alert("Error Quitting battle:", error);
        }
    }

    // Function to send an invite
    // Function to send an invite  requesterId invitedId  groupId
    const inviteUser = async (email) => {
        const url = new URL(`${API_URL}/getId/${email}`);
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('La richiesta non è andata a buon fine');
                }
                return response.json(); // Assicurati di restituire il risultato di response.json() per il successivo .then()
            })
            .then(data => {
                console.log(data.userId); // Questo log viene eseguito dopo aver ricevuto la risposta
                setreceiverId(data.userId);
                console.log(receiverId); // Assicurati che questa variabile sia accessibile o definisci la logica qui
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
            });

        try {
            const payload = {
                studentId: receiverId,
                groupId: groupId
            };

            await axios.post(`${API_URL}/battle/group`, payload);
        } catch (error) {
            console.error("Error sending invite", error);
        }
    };

    const changeGroup = async () => {
        const payload = {
            studentId: userId, // Assuming userId is the current user's ID
            groupId: newGroupId // The new group ID entered by the user
        };

        try {
            const response = await axios.put(`${API_URL}/battle/group`, payload);
            if (response.status === 200) {
                alert("Group changed successfully");
                setGroupId(newGroupId); // Update the current groupId state
                navigate(`/successpage`); // Navigate to a success page or reload current component
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error("Error changing group", error);
            alert("Error changing group:", error);
        }
    };
//             <button onClick={leaveGroup}>Leave Group</button>
    return (
        <div className="group-component">
            <h2>Your group ID: {groupId}</h2>
            <h3>Users in battle:</h3>
            <ul>
                {userEmails.map((email, index) => (
                    <li key={index}>{email} <button onClick={() => inviteUser(email)}>Invite</button> </li>
                ))}
            </ul>
            <div className="change-group-container">
                <input
                    type="text"
                    placeholder="Enter new group ID"
                    value={newGroupId}
                    onChange={(e) => setNewGroupId(e.target.value)}
                />
                <button onClick={changeGroup}>Change Group</button>
            </div>
        </div>
    );
};

export default GroupComponent;
