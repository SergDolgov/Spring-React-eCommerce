import { createContext, useReducer, useState, useEffect } from 'react';
import commonReducer from './commonReducer';

// Common-Context
const commonContext = createContext();

// Initial State
const initialState = {
    user: null,
    userIsAuthenticated: false,
    isFormOpen: false,
    formUserInfo: '',
    isSearchOpen: false,
    searchResults: [],
};

// Common-Provider Component
const CommonProvider = ({ children }) => {

    const [state, dispatch] = useReducer(commonReducer, initialState);
    const [user, setUser] = useState(null)

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'))
        setUser(storedUser)
    }, [])

    const getUser = () => {
        return JSON.parse(localStorage.getItem('user'))
    }

    const userIsAuthenticated = () => {
        let storedUser = localStorage.getItem('user')
        if (!storedUser) {
          return false
        }
        storedUser = JSON.parse(storedUser)

        // if user has token expired, logout user
        if (Date.now() > storedUser.data.exp * 1000) {
          userLogout()
          return false
        }
        return true
    }

    const userLogin = user => {
        localStorage.setItem('user', JSON.stringify(user))
        setUser(user)
    }

    // Form actions
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

    const userLogout = () => {
        localStorage.removeItem('user')
        return dispatch({
            type: 'LOGOUT',
            payload: {  }
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
        setFormUserInfo,
        toggleSearch,
        setSearchResults,
        user,
        getUser,
        userIsAuthenticated,
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