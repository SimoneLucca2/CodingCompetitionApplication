import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StudentProfile from './StudentProfile';
import config from '../../config';
import PropTypes from "prop-types";

export function StudentProfilePage(props) {
    const [studentData, setStudentData] = useState(null);

    useEffect(() => {
        const fetchStudentData = async () => {
            try {
                const response = await axios.get(`${config.API_URL}/user/student/${props.studentId}`);
                setStudentData(response.data);
            } catch (error) {
                console.error('Failed to fetch student data:', error);
                // Handle the error, e.g., set an error state, show a message, etc.
            }
        };

        fetchStudentData();
    }, []); // The empty dependency array ensures this effect runs once after the component mounts

    // Conditional rendering - show StudentProfile only when studentData is available
    return (
        studentData ?
            <StudentProfile user={studentData} tournaments={studentData.tournaments || []} /> :
            null // or replace with any loading component
    );
}

StudentProfilePage.propTypes = {
    studentId: PropTypes.number.isRequired,
}