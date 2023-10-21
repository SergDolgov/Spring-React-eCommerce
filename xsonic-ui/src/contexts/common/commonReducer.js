const commonReducer = (state, action) => {
    switch (action.type) {

        case 'TOGGLE_FORM':
            return {
                ...state,
                isFormOpen: action.payload.toggle
            };


        case 'TOGGLE_FORM_PRODUCT':
            return {
                ...state,
                isFormProductOpen: action.payload.toggle
            };


        case 'SET_FORM_USER_INFO':
            return {
                ...state,
                formUserInfo: action.payload.info
            };


        case 'SET_FORM_PRODUCT_INFO':
            return {
                ...state,
                formProductInfo: action.payload.product
            };


        case 'TOGGLE_SEARCH':
            return {
                ...state,
                isSearchOpen: action.payload.toggle
            };


        case 'SET_SEARCH_RESULTS':
            return {
                ...state,
                searchResults: action.payload.results
            };

        case 'LOGOUT':
            return {
                ...state,
                formUserInfo: action.payload.info
            };


        default:
            return state;
    }
};

export default commonReducer;