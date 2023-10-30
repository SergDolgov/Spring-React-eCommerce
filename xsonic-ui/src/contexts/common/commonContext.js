import { createContext, useReducer, useEffect } from 'react';
import commonReducer from './commonReducer';

// Common-Context
const commonContext = createContext();

// Initial State
const initialState = {
    user: null,
    userName: '',
    userRole: '',
    isFormOpen: false,
    isProductUpdated: true,
    isProductFormOpen: false,
    isSearchOpen: false,
    searchResults: [],
};

// Common-Provider Component
const CommonProvider = ({ children }) => {

    const [state, dispatch] = useReducer(commonReducer, initialState);

    function getUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    // User login action
    const userLogin = user => {
        localStorage.setItem('user', JSON.stringify(user))
        return dispatch({
            type: 'USER_LOGIN',
            payload: {user}
        });
    }

    useEffect(() => {
         const storedUser = getUser()
         if(storedUser){userLogin(storedUser)}
    }, [])

    // User logout action
    const userLogout = () => {
        localStorage.removeItem('user')
        return dispatch({
            type: 'USER_LOGOUT'//,
            //payload: {}
        });
    };

    // Form login actions
    const toggleForm = (toggle) => {
        return dispatch({
            type: 'TOGGLE_FORM',
            payload: { toggle }
        });
    };

    const setFormUserInfo = (info) => {
            return dispatch({
            type: 'SET_FORM_USER_INFO',
            payload: { info }
        });
    };

   // Form product actions
    const toggleProductForm = (toggle) => {
        return dispatch({
            type: 'TOGGLE_FORM_PRODUCT',
            payload: { toggle }
        });
    };

    const setIsProductUpdated = (updated) => {
        return dispatch({
            type: 'SET_PRODUCT_UPDATED',
            payload: { updated }
        });
    };

    // Search actions
    const toggleSearch = (toggle) => {
        return dispatch({
            type: 'TOGGLE_SEARCH',
            payload: { toggle }
        });
    };

    const setSearchResults = (results) => {
        return dispatch({
            type: 'SET_SEARCH_RESULTS',
            payload: { results }
        });
    };


    // Context values
    const values = {
        ...state,
        toggleForm,
        toggleProductForm,
        setIsProductUpdated,
        setFormUserInfo,
        toggleSearch,
        setSearchResults,
        getUser,
        userLogin,
        userLogout
    };

    return (
        <commonContext.Provider value={values}>
            {children}
        </commonContext.Provider>
    );
};

export default commonContext;
export { CommonProvider };