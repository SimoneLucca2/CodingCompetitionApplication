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

    const status = params.status;
    console.log(status);

    useEffect(() => {
        const fetchGroupId = async () => {
            try {
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
                }
            } catch (error) {
                console.error("Error fetching user emails", error);
                alert("Error fetching user emails");
            }
        };

        fetchGroupId();
        fetchUserEmails();

    }, [userId, battleId]);


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

    const inviteUser = async (email) => {
        try {
            const url = `${API_URL}/getId/${email}`;
            const response = await axios.get(url);
            setreceiverId(response.data.userId);
            const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));
            await delay(3000);
            console.log("Sono passati 3 secondi");
        } catch (error) {
            console.error("Error fetching group ID", error);
            alert("Error fetching group ID");
        }

        try {
            console.log(receiverId, groupId);
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
            studentId: userId,
            groupId: newGroupId
        };

        try {
            const response = await axios.put(`${API_URL}/battle/group`, payload);
            if (response.status === 200) {
                alert("Group changed successfully");
                setGroupId(newGroupId);
                navigate(`/successpage`);
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
            <h2>Your group ID: {groupId}</h2>
            {status === 'PRE_BATTLE' && (
                <>
                    <h3>Users in battle:</h3>
                    <ul>
                        {userEmails.map((email, index) => (
                            <li key={index}>{email} <button onClick={() => inviteUser(email)}>Invite</button></li>
                        ))}
                    </ul>
                    <div className="change-group-container">
                        <input
                            type="text"
                            placeholder="Enter group ID you received from the email"
                            value={newGroupId}
                            onChange={(e) => setNewGroupId(e.target.value)}
                        />
                        <button onClick={changeGroup}>Change Group</button>
                    </div>
                </>
            )}
        </div>
    );
};

export default GroupComponent;
