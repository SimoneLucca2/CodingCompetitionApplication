import axios from 'axios';

export function useHttp() {
    return async ({url, method, data}) => {
        try {
            const response = await axios({
                url,
                method,
                data: (method !== 'get') ? data : undefined,
                params: (method === 'get') ? data : undefined,
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    };
}
