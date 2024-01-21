import React, { useState, useEffect } from 'react';
import axios from 'axios';

export function ProfilePicture() {
    const [imageUrl, setImageUrl] = useState(null); // State to store the image URL
    const defaultImageUrl = './Assets/unknownUser.png';

    useEffect(() => {
        // Function to fetch the profile picture
        const fetchProfilePicture = async () => {
            try {
                const response = await axios.get('Your API Endpoint Here');
                if (response.data && response.data.imageUrl) {
                    setImageUrl(response.data.imageUrl);
                } else {
                    setImageUrl(defaultImageUrl);
                }
            } catch (error) {
                console.error('Error fetching profile picture:', error);
                setImageUrl(defaultImageUrl);
            }
        };

        fetchProfilePicture();
    }, []); // Empty dependency array means this effect runs once on component mount

    return (
        //TODO: Add an api endpoint to fetch the profile picture
        <img src={/*imageUrl ||*/ defaultImageUrl} alt="Profile" />
    );
}
