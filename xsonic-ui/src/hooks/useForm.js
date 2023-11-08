import { useContext, useState } from 'react';
import commonContext from '../contexts/common/commonContext';
import { productApi } from '../helpers/productApi'
import { parseJwt, handleLogError } from '../helpers/utils'

const useForm = () => {

    const { toggleForm, userLogin } = useContext(commonContext);
    const [inputValues, setInputValues] = useState({});
    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')

    // handling input-values
    const handleChangeInputValues = (e) => {
        const { name, value } = e.target;

        setInputValues((prevValues) => {
            return {
                ...prevValues,
                [name]: value
            };
        });
    };

    // handling form-submission
    const handleFormSubmit = async (e) => {
       e.preventDefault();
       !inputValues.username ? handleUserLogin(e):handleUserRegistration(e)
    }

    // handling user login
    const handleUserLogin = async (e) => {

       const user = {
           email: inputValues.email,
           password: inputValues.password
       };

       try {
           const response = await productApi.login(user);
           const { token } = response.data;
           const data = parseJwt(token);
           const authenticatedUser = { data, token };

           handleSuccessfulLogin(authenticatedUser);
       } catch (error) {
           handleLoginError(error);
       }
    };

    // handling user registration
    const handleUserRegistration = async (e) => {

       const user = {
           firstName: inputValues.username,
           lastName: '',
           email: inputValues.email,
           password: inputValues.password,
           password2: inputValues.conf_password,
           captcha: "captcha"
       };

       try {
           const response = await productApi.registration(user);
           const { token } = response.data;
           const data = parseJwt(token);
           const authenticatedUser = { data, token };

           handleSuccessfulLogin(authenticatedUser);
       } catch (error) {
           handleRegistrationError(error);
       }
    };

    // handling general function to access user
    const handleSuccessfulLogin = (authenticatedUser) => {

       userLogin(authenticatedUser);

       setInputValues({});
       toggleForm(false);
       setIsError(false);
       setErrorMessage('');

       // alert(`Hello ${loggedUserInfo}, you're successfully logged-in.`);
    };

    //handling login errors
    const handleLoginError = (error) => {
       handleLogError(error);
       if (error.response && error.response.data) {
           const errorMessage = error.response.data;
           setIsError(true);
           setErrorMessage(errorMessage);
       }
    };

    // handling registration errors
    const handleRegistrationError = (error) => {
       handleLogError(error);
       if (error.response && error.response.data) {
           const errorData = error.response.data;
           let errorMessage = 'Invalid fields'
           if (error.response.status === 400) {
               errorMessage = errorData.passwordError ? errorData.passwordError : errorData.password2Error ? errorData.password2Error : errorData.emailError
           }

           setIsError(true);
           setErrorMessage(errorMessage);
       }
    };

    return { inputValues, handleChangeInputValues, setInputValues, handleFormSubmit, isError, errorMessage};
};

export default useForm;