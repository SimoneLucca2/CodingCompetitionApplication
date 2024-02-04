import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ParticipateGroup.css';
import API_URL from "../../config";
import {useNavigate, useParams} from "react-router-dom";

const GroupComponent = () => {
    const [groupId, setGroupId] = useState('');
    const [userEmails, setUserEmails] = useState(['example1@example.com',
        'example2@example.com',
        'example3@example.com']);
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
                navigate(`/errorpage`, { replace: true });
            }
        };

        const fetchUserEmails = async () => {
            try {
                const url = `${API_URL}/battle/group/students/${battleId}`;
                const response = await axios.get(url);
                const studentIds = response.data;

                const emails = await Promise.all(studentIds.map(async (id) => {
                    const url = `${API_URL}/user/email/${encodeURIComponent(id)}`;
                    const { data: userData } = await axios.get(url);
                    return userData.email;
                }));
                setUserEmails(emails);
            } catch (error) {
                console.error("Error fetching user emails", error);
                navigate(`/errorpage`, { replace: true });
            }
        };

        fetchGroupId();
        fetchUserEmails();

    }, [userId, battleId]);



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

    // Function to send an invite
    const inviteUser = async (email) => {
        try {
            await axios.post('/api/user/invite', { email });
            alert(`Invite sent to ${email}`);
        } catch (error) {
            console.error("Error sending invite", error);
        }
    };

    return (
        <div className="group-component">
            <h2>Your group ID: {groupId}</h2>
            <button onClick={leaveGroup}>Leave Group</button>
            <h3>Users in battle:</h3>
            <ul>
                {userEmails.map((email, index) => (
                    <li key={index}>{email} <button onClick={() => inviteUser(email)}>Invite</button></li>
                ))}
            </ul>
        </div>
    );
};

export default GroupComponent;
