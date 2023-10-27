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
    isProductFormOpen: false,
    isSearchOpen: false,
    searchResults: [],
};

// Common-Provider Component
const CommonProvider = ({ children }) => {

    const [state, dispatch] = useReducer(commonReducer, initialState);
//    const [user, setUser] = useState(null)
//
//
//    const getUser = () => {
//        const storedUser = JSON.parse(localStorage.getItem('user'))
//        return dispatch({
//            type: 'GET_USER',
//            payload: {storedUser}
//        });
//    }

    // User login action
    const userLogin = user => {
        localStorage.setItem('user', JSON.stringify(user))
        return dispatch({
            type: 'USER_LOGIN',
            payload: {user}
        });
    }

    useEffect(() => {
         const storedUser = JSON.parse(localStorage.getItem('user'))
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
        setFormUserInfo,
        toggleSearch,
        setSearchResults,
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