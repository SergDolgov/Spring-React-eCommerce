import { useContext, useState } from 'react';
import commonContext from '../contexts/common/commonContext';

const useFormProduct = () => {
    const { toggleFormProduct, setFormProductInfo, formProductInfo } = useContext(commonContext);
    const [inputValues, setInputValues] = useState({});

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

    // handling set input-values
    const handleSetInputValues = (e) => {
        setInputValues(formProductInfo);
    };

    // handling form-saving
    const handleSaveProduct = (e) => {
        //const loggedUserInfo = inputValues.title.split('@')[0].toUpperCase();
        //setFormProductInfo(inputValues);

        e.preventDefault();
        setInputValues({});
        toggleFormProduct(false);
        alert(`Hello, you're save product.`);
    };

    return { inputValues, handleSetInputValues, handleInputValues, handleSaveProduct };
};

export default useFormProduct;