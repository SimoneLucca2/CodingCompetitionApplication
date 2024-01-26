import React from 'react';
import Button from './Button';
import { useHttp } from './useHttp';

export function GetButton({ url, params, buttonText, onSuccess }) {
    const sendRequest = useHttp();

    const handleClick = async () => {
        try {
            // Note: If your GET request requires parameters, they should be passed as query parameters in the URL.
            // Adjust the sendRequest call accordingly if needed.
            const responseData = await sendRequest({ url, method: 'get', params });
            onSuccess(responseData);
        } catch (error) {
            console.error("Error in GET request: ", error);
        }
    };

    return <Button onClick={handleClick} buttonText={buttonText} />;
}
