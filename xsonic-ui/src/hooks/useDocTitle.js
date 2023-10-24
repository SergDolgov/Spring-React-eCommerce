import { useEffect } from 'react';

const useDocTitle = (title) => {
    useEffect(() => {
        if (title) {
            document.title = `${title} - X-Sonic`;
        } else {
            document.title = 'XSonic | The Perfect Audio Store';
        }
    }, [title]);

    return null;
};

export default useDocTitle;
