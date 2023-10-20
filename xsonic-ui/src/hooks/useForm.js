import { useContext, useState } from 'react';
import commonContext from '../contexts/common/commonContext';
import { productApi } from '../misc/productApi'
import { parseJwt, handleLogError } from '../misc/helpers'

const useForm = () => {

    const { toggleForm, setFormUserInfo, userLogin } = useContext(commonContext);
    const [inputValues, setInputValues] = useState({});
    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')

    // handling input-values
    const handleInputValues = (e) => {
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
        const loggedUserInfo = inputValues.email.split('@')[0].toUpperCase();

        e.preventDefault();

        let user = !inputValues.username ?
            {
                email: inputValues.email,
                password: inputValues.password
            }:
            {
                firstName: inputValues.username,
                lastName: inputValues.username,
                email: inputValues.email,
                password: inputValues.password,
                password2: inputValues.conf_password,
                captcha: "captcha"
            }

        try {
          const response = (inputValues.username === undefined) ? await productApi.login(user): await productApi.registration(user)

          const { token } = response.data
          const data = parseJwt(token)
          const authenticatedUser = { data, token }

          setFormUserInfo(loggedUserInfo);
          userLogin(authenticatedUser);

          setInputValues({});
          toggleForm(false);
          alert(`Hello ${loggedUserInfo}, you're successfully logged-in.`);

          setIsError(false)
          setErrorMessage('')
        } catch (error) {
          handleLogError(error)
          if (error.response && error.response.data) {
            const errorData = error.response.data
            let errorMessage = 'Invalid fields'
            if (errorData.status === 409) {
              errorMessage = errorData.message
            } else if (errorData.status === 400) {
              errorMessage = errorData.errors[0].defaultMessage
            } else if (error.response.status === 404 || errorData.status === 404) {
              errorMessage = errorData
            }
            setIsError(true)
            setErrorMessage(errorMessage)
          }
        }

    };

    return { inputValues, handleInputValues, handleFormSubmit, isError, errorMessage};
};

export default useForm;