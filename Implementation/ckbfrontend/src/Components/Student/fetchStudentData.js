import axios from 'axios';
import config from '../../config';


export async function fetchStudentData() {

    //TODO - get jwtToken from local storage or server
    const jwtToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c';
    try {
        const response = await axios.get(`${config.API_URL}/user/{props.studentId}`, {
            headers: {
                Authorization: `Bearer ${jwtToken}`
            }
        });

        // Process the response data
        console.log(response.data);
    } catch (error) {
        console.error('Error fetching student data:', error);
    }
}

fetchStudentData();
