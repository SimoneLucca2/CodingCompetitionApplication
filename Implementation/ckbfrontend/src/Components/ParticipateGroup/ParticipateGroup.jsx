import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ParticipateGroup.css';
import API_URL from "../../config";
import {useNavigate, useParams} from "react-router-dom";

const ParticipateGroup = () => {
    const params = useParams();
    const battle = params.battleId;
    const battleId = parseInt(battle, 10);
    const [groupId, setGroupId] = useState('');
    const [receiverId, setreceiverId] = useState('');
    const [newGroupId, setNewGroupId] = useState('');

    const [userEmails, setUserEmails] = useState([/*'example1@example.com',
        'example2@example.com',
        'example3@example.com'*/]);
    const navigate = useNavigate();

    useEffect(() => {
        const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
        if (!oggettoSalvato) {
            navigate(`/needauthentication`, { replace: true });
            return;
        }

        const userId = oggettoSalvato?.userId;

        const fetchGroupId = async () => {
            try {
                const url = `${API_URL}/battle/group/id/${battleId}/${userId}`;
                const response = await axios.get(url);
                setGroupId(response.data.groupId);
            } catch (error) {
                console.error("Error fetching group ID", error);
            }
        };

        const fetchUserEmails = async () => {
            if (!groupId) return;
            try {
                const url = `${API_URL}/battle/group/students/${battleId}/${groupId}`;
                const response = await axios.get(url);
                const studentIds = response.data;
                console.log(studentIds);

                const emails = await Promise.all(studentIds.map(async (studentId) => {
                    const url = `${API_URL}/user/email/${studentId}`;
                    const { data: userData } = await axios.get(url);
                    return userData.email;
                }));
                setUserEmails(emails);
            } catch (error) {
                console.error("Error fetching user emails", error);
            }
        };

        fetchGroupId().then(fetchUserEmails);

    }, [navigate, battleId, groupId]);



    const oggettoSalvato = JSON.parse(sessionStorage.getItem('utente'));
    const userId = oggettoSalvato?.userId;


    // Function to leave the group
    async function leaveGroup() {

        const requestBody = {
            studentId: userId,
            groupId: battle.battleId
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
            navigate(`successpage`);

        } catch (error) {
            alert("Error Quitting battle:", error);
        }
    }

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
                    alert("Error fetching user ID");
                }
                setreceiverId(response.data.userId);
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
            });

        try {
            const payload = {
                requesterId: userId, // Supponendo che 'email' sia già definita
                receiverId: receiverId, // Sostituisci 'receiverId' con il valore effettivo
                groupId: groupId // Sostituisci 'groupId' con il valore effettivo
            };

            await axios.post(`${API_URL}/battle/group`, payload);
            alert(`Invite sent to ${email}`);
        } catch (error) {
            console.error("Error sending invite", error);
        }
    };

    const changeGroup = async () => {
        const payload = {
            studentId: userId,
            groupId: newGroupId
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

    return (
        <div className="group-component">
            <div className="header">
                <h2>Your group ID: {groupId}</h2>
                <button className="leave-btn" onClick={leaveGroup}>Leave Group</button>
            </div>
            <h3>Users in battle:</h3>
            <ul>
                {userEmails.map((email, index) => (
                    <li key={index}>{email}
                        <button className="invite-btn" onClick={() => inviteUser(email)}>Invite</button>
                    </li>
                ))}
            </ul>
            <div className="change-group">
                <input
                    type="text"
                    placeholder="Enter new group ID"
                    value={newGroupId}
                    onChange={(e) => setNewGroupId(e.target.value)}
                />
                <button className="change-btn" onClick={changeGroup}>Change Group</button>
            </div>
        </div>
    );
};

export default ParticipateGroup;
