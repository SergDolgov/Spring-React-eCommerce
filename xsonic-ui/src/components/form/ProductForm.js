import React, { useContext, useRef, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import commonContext from '../../contexts/common/commonContext';
import useFormProduct from '../../hooks/useFormProduct';
import useOutsideClose from '../../hooks/useOutsideClose';
import useScrollDisable from '../../hooks/useScrollDisable';

const ProductForm = () => {

    const { isFormProductOpen, toggleFormProduct } = useContext(commonContext);
    const { inputValues, handleSetInputValues, handleInputValues, handleSaveProduct, isError, errorMessage } = useFormProduct();
    const [ isNewProduct, setIsNewProduct] = useState(true);

//    if (formProductInfo != null) {
        handleSetInputValues();
//        setIsNewProduct(false)
//    }

    const formRef = useRef();

    useOutsideClose(formRef, () => {
        toggleFormProduct(false);
    });

    useScrollDisable(isFormProductOpen);

    return (
        <>
            {
                isFormProductOpen && (
                    <div className="backdrop">
                        <div className="modal_centered">
                            <form id="account_form" ref={formRef} onSubmit={handleSaveProduct}>

                                {/*===== Form-Header =====*/}
                                <div className="form_head">
                                    <h2>{isNewProduct ? 'Add new product' : 'Edit product'}</h2>
                                </div>

                                {/*===== Form-Body =====*/}
                                <div className="form_body">
                                    <div className="input_box">
                                        <input
                                            type="brand"
                                            name="brand"
                                            className="input_field"
                                            value={inputValues.brand || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Brand</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="title"
                                            name="title"
                                            className="input_field"
                                            value={inputValues.title || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Title</label>
                                    </div>

                                    <div className="input_box">
                                        <input
                                            type="info"
                                            name="info"
                                            className="input_field"
                                            value={inputValues.info || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Info</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="category"
                                            name="category"
                                            className="input_field"
                                            value={inputValues.category || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Category</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="type"
                                            name="type"
                                            className="input_field"
                                            value={inputValues.type || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Type</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="connectivity"
                                            name="connectivity"
                                            className="input_field"
                                            value={inputValues.connectivity || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">Connectivity</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="finalPrice"
                                            name="finalPrice"
                                            className="input_field"
                                            value={inputValues.finalPrice || ''}
                                            onChange={handleInputValues}
                                            required
                                        />
                                        <label className="input_label">FinalPrice</label>
                                    </div>

                                    {isError && <label style={{color: 'red'}}>{errorMessage}</label>}
                                    <button
                                        type="submit"
                                        className="btn login_btn"
                                    >
                                        Save
                                    </button>
                                    <button
                                        type="submit"
                                        className="btn login_btn"
                                    >
                                        Cancel
                                    </button>

                                </div>

                                {/*===== Form-Close-Btn =====*/}
                                <div
                                    className="close_btn"
                                    title="Close"
                                    onClick={() => toggleFormProduct(false)}
                                >
                                    &times;
                                </div>

                            </form>
                        </div>
                    </div>
                )
            }
        </>
    );
};

export default ProductForm;