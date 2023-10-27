import { useContext, useState } from 'react';
import commonContext from '../contexts/common/commonContext';
import { productApi } from '../helpers/productApi'
import { handleLogError } from '../helpers/utils'

const useProductForm = () => {

    const { toggleProductForm, user } = useContext(commonContext);
    const [inputValues, setInputValues] = useState({});
    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')
    const [file, setFile] = useState('');
    //const [file, setFile] = React.useState<string>("");

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

    const handleUpload = (e) => {
        console.log('picture: ', file);
        setFile(e.target.files[0]);
    };

    // handling form-submission
    const handleFormSubmit = async (e) => {
       e.preventDefault();
       
       const bodyFormData: FormData = new FormData();
       // @ts-ignore
       bodyFormData.append("file", file);
       bodyFormData.append(
           "product",
           new Blob([JSON.stringify({ ...inputValues})], { type: "application/json" })
       );

       !inputValues.id ? handleAddProduct(bodyFormData):handleUpdateProduct(bodyFormData)
    }

    // handling add product
    const handleAddProduct = async (bodyFormData) => {

       try {
           await productApi.addProduct(user, bodyFormData);
           handleSuccessful();
       } catch (error) {
           handleAddProductError(error);
       }
    };

    // handling product registration
    const handleUpdateProduct = async (bodyFormData) => {

       try {
           await productApi.updateProduct(user, bodyFormData);
           handleSuccessful();
       } catch (error) {
           handleUpdateProductError(error);
       }
    };

    // handling general function to add/update product
    const handleSuccessful = () => {

       setInputValues({});
       toggleProductForm(false);
       setIsError(false);
       setErrorMessage('');
       // alert(`Hello ${loggedUserInfo}, you're successfully logged-in.`);
    };

    //handling login errors
    const handleAddProductError = (error) => {
       handleLogError(error);
       if (error.response && error.response.data) {
           const errorMessage = error.response.data;
           setIsError(true);
           setErrorMessage(errorMessage);
       }
    };

    // handling registration errors
    const handleUpdateProductError = (error) => {
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

    return { inputValues, setInputValues, handleChangeInputValues, file, handleUpload, handleFormSubmit, isError, errorMessage};
};

export default useProductForm;