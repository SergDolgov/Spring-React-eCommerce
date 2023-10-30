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
                isProductFormOpen: action.payload.toggle
            };


        case 'SET_FORM_USER_INFO':
            return {
                ...state,
                userName: action.payload.info
            };


        case 'SET_FORM_PRODUCT_INFO':
            return {
                ...state,
                formProductInfo: action.payload.product
            };


        case 'SET_PRODUCT_UPDATED':
            return {
                ...state,
                isProductUpdated: action.payload.updated
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


        case 'GET_USER':
            return {
                ...state,
                user: action.payload.user,
                userName: action.payload.user.data.sub.split('@')[0].toUpperCase(),
                userRole: action.payload.user.data.role
            };


        case 'USER_LOGIN':
            const newUser = action.payload.user
            return {
                ...state,
                user: newUser,
                userName: newUser.data.sub.split('@')[0].toUpperCase(),
                userRole: newUser.data.role
            };


        case 'USER_LOGOUT':
            return {
                ...state,
                user: null,
                userName: '',
                userRole: ''
            };


        default:
            return state;
    }
};

export default commonReducer;