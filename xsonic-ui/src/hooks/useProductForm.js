import { useContext, useState } from 'react';
import commonContext from '../contexts/common/commonContext';
import { productApi } from '../helpers/productApi'
import { handleLogError } from '../helpers/utils'

const useProductForm = () => {

    const { toggleProductForm, user } = useContext(commonContext);
    const [inputValues, setInputValues] = useState({});
//    const [isError, setIsError] = useState(false)
//    const [errorMessage, setErrorMessage] = useState('')
    const [file, setFile] = useState('');
    //const [file, setFile] = React.useState<string>("");

    const [isSave, setIsSave] = useState(false)


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
//    const handleSaveProduct = async (e) => {
//       e.preventDefault();
//
//       const bodyFormData: FormData = new FormData();
//       // @ts-ignore
//       bodyFormData.append("file", file);
//       bodyFormData.append(
//           "product",
//           new Blob([JSON.stringify({ ...inputValues})], { type: "application/json" })
//       );
//
//       !inputValues.id ? handleAddProduct(bodyFormData):handleUpdateProduct(bodyFormData)
//
//    }
//
//    // handling add product
//    const handleAddProduct = async (bodyFormData) => {
//
//       try {
//           await productApi.addProduct(user, bodyFormData);
//           clearProductForm();
//       } catch (error) {
//           handleAddProductError(error);
//       }
//    };
//
//    // handling update product
//    const handleUpdateProduct = async (bodyFormData) => {
//
//       try {
//           await productApi.updateProduct(user, bodyFormData);
//           clearProductForm();
//       } catch (error) {
//           handleUpdateProductError(error);
//       }
//    };
//
//    // clear values after add/update product
//    const clearProductForm = () => {
//       setInputValues({});
//       toggleProductForm(false);
//       setIsError(false);
//       setIsSave(true);
//       setErrorMessage('');
//       // alert(`Hello ${loggedUserInfo}, you're successfully logged-in.`);
//    };
//
//    //handling login errors
//    const handleAddProductError = (error) => {
//       handleLogError(error);
//       if (error.response && error.response.data) {
//           const errorMessage = error.response.data;
//           setIsError(true);
//           setErrorMessage(errorMessage);
//       }
//    };
//
//    // handling update product errors
//    const handleUpdateProductError = (error) => {
//       handleLogError(error);
//       if (error.response && error.response.data) {
//           const errorData = error.response.data;
//           let errorMessage = 'Invalid fields'
//           if (error.response.status === 400) {
//               errorMessage = errorData.passwordError ? errorData.passwordError : errorData.password2Error ? errorData.password2Error : errorData.emailError
//           }
//
//           setIsError(true);
//           setErrorMessage(errorMessage);
//       }
//    };

    return {
        inputValues,
        setInputValues,
        handleChangeInputValues,
        file,
        handleUpload//,
//        handleSaveProduct,
//        clearProductForm,
//        isSave,
//        isError,
//        setIsError,
//        errorMessage,
//        setErrorMessage
    };
};

export default useProductForm;