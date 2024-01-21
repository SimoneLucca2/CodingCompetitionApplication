import React from 'react';
import Button from './Button';
import { useHttp } from './useHttp';

export function PutButton({ url, data, buttonText, onSuccess }) {
    const sendRequest = useHttp();

    const handleClick = async () => {
        try {
            const responseData = await sendRequest({ url, method: 'put', data });
            onSuccess(responseData);
        } catch (error) {
            console.error("Error in PUT request: ", error);
        }
    };

    return <Button onClick={handleClick} buttonText={buttonText} />;
}
