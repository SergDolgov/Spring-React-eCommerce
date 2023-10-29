import React, { useContext, useState, useEffect} from 'react';
import { BsExclamationCircle, BsPencilSquare, BsTrash, BsPlusSquare, BsFiles } from 'react-icons/bs';
import useDocTitle from '../hooks/useDocTitle';
import FilterBar from '../components/filters/FilterBar';
import Services from '../components/common/Services';
import filtersContext from '../contexts/filters/filtersContext';
import commonContext from '../contexts/common/commonContext';
import EmptyView from '../components/common/EmptyView';
import useProductForm from '../hooks/useProductForm';
import ProductForm from '../components/form/ProductForm';
import { productApi } from '../helpers/productApi'
import { handleLogError } from '../helpers/utils'


const AdminProducts = () => {
    useDocTitle('Admin Products');

    const { toggleProductForm, getUser } = useContext(commonContext);

    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')
    const { inputValues, setInputValues, handleChangeInputValues, file, handleUpload } = useProductForm();

    const user = getUser();
    //const { allProducts } = useContext(filtersContext);
    const [allProducts, setAllProducts] = useState([])

    const [selectedProduct, setSelectedProduct] = useState({});
    const [isProductsLoading, setIsProductsLoading] = useState(false)
    const [isUpdated, setIsUpdated] = useState(true)

    useEffect(() => {
        // get all products
        const fetchData = async () => {
            if (isUpdated){
                try {
                    setIsProductsLoading(true)
                    const response = await productApi.getAdminProducts(user,'');
                    setAllProducts(response.data)
                } catch (error) {
                    handleLogError(error);
                    if (error.response && error.response.data) {
                       const errorMessage = error.response.data;
                       setIsError(true);
                       setErrorMessage(errorMessage);
                    }
                } finally {
                    setIsProductsLoading(false)
                    setIsUpdated(false)
                }
            }
        }

        fetchData();

    }, [isUpdated, user])

    // handling add product
    const handleAddProductForm = () => {
        setSelectedProduct({});
        toggleProductForm(true)
    };

    // handling update product
    const handleUpdateProductForm = () => {
        if (selectedProduct != null) {
            toggleProductForm(true)
        }
    };

    // handling add copy product
    const handleCopyProduct = () => {
        if (selectedProduct != null) {
            setSelectedProduct({ ...selectedProduct, id: 0 });
            toggleProductForm(true)
        }
    };

    // handling delete product
    const handleDeleteProduct = async () => {
        if (selectedProduct != null) {
            try {
                await productApi.deleteProduct(user, selectedProduct.id);
                 setIsUpdated(true);
                //alert('The product was successfully deleted!');
            } catch (error) {
                handleLogError(error);
                if (error.response && error.response.data) {
                   const errorMessage = error.response.data;
                   setIsError(true);
                   setErrorMessage(errorMessage);
                }
            }
        }
    };



    const handleSaveProduct = async (e) => {
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
           clearProductForm();
       } catch (error) {
           handleAddProductError(error);
       }
    };

    // handling update product
    const handleUpdateProduct = async (bodyFormData) => {

       try {
           await productApi.updateProduct(user, bodyFormData);
           clearProductForm();
       } catch (error) {
           handleUpdateProductError(error);
       }
    };

    // clear values after add/update product
    const clearProductForm = () => {
       setInputValues({});
       toggleProductForm(false);
       setIsError(false);
       setIsUpdated(true);
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

    // handling update product errors
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




    const handleFormSubmit = (e) => {
        try {
            handleSaveProduct(e)
            clearProductForm()
        } catch (error) {
            handleLogError(error)
            if (error.response && error.response.data) {
               const errorMessage = error.response.data;
               setIsError(true);
               setErrorMessage(errorMessage);
            }
        }
    }


    return (
        <>
            <section id="admin_products" className="section">
                <FilterBar />
                <div className="container">
                    <div className="actions">
                        <BsPlusSquare onClick={handleAddProductForm} className="action-icon" />
                        <BsFiles  onClick={handleCopyProduct} className="action-icon" />
                        <BsPencilSquare onClick={handleUpdateProductForm} className="action-icon" />
                        <BsTrash onClick={handleDeleteProduct} className="action-icon" />
                    </div>
                    {allProducts.length ? (
                        <table className="product-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Brand</th>
                                    <th>Title</th>
                                    <th>Info</th>
                                    <th>Category</th>
                                    <th>Type</th>
                                    <th>Connectivity</th>
                                    <th>OriginalPrice</th>
                                    <th>FinalPrice</th>
                                    <th>Quantity</th>
                                </tr>
                            </thead>
                            <tbody>
                                {allProducts.map(item => (
                                    <tr
                                        key={item.id}
                                        className={selectedProduct === item ? 'selected-row' : ''}
                                        onClick={() => setSelectedProduct(item)}
                                    >
                                        <td>{item.id}</td>
                                        <td>{item.brand}</td>
                                        <td>{item.title}</td>
                                        <td>{item.info}</td>
                                        <td>{item.category}</td>
                                        <td>{item.type}</td>
                                        <td>{item.connectivity}</td>
                                        <td>${item.originalPrice}</td>
                                        <td>${item.finalPrice}</td>
                                        <td>${item.quantity}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <EmptyView icon={<BsExclamationCircle />} msg="No Results Found" />
                    )}
                </div>
            </section>
            <Services />
            <ProductForm
                selectedProduct = {selectedProduct}
                inputValues = {inputValues}
                setInputValues = {setInputValues}
                handleChangeInputValues = {handleChangeInputValues}
                file = {file}
                handleUpload = {handleUpload}
                handleFormSubmit = {handleFormSubmit}
                isError = {isError}
                errorMessage = {errorMessage}
            />
        </>
    );
};

export default AdminProducts;
