import React, { useContext, useRef, useEffect } from 'react';
import useProductForm from '../../hooks/useProductForm';
import commonContext from '../../contexts/common/commonContext';
import useOutsideClose from '../../hooks/useOutsideClose';
import useScrollDisable from '../../hooks/useScrollDisable';

const ProductForm = ({selectedProduct}) => {

    const { isProductFormOpen, toggleProductForm } = useContext(commonContext);
    const { inputValues, setInputValues, handleChangeInputValues, file, handleUpload, handleFormSubmit, isError, errorMessage } = useProductForm();

    useEffect(() => {
        setInputValues(selectedProduct);
    }, [setInputValues, selectedProduct]);

    const formRef = useRef();

    useOutsideClose(formRef, () => {
        toggleProductForm(false);
    });

    useScrollDisable(isProductFormOpen);

    return (
        <>
            {
                isProductFormOpen && (
                    <div className="backdrop">
                        <div className="modal_centered">
                            <form id="product_form" ref={formRef} onSubmit={handleFormSubmit}>

                                {/*===== Form-Header =====*/}
                                <div className="form_head">
                                    <h2>{inputValues.id ? 'Update product': 'Add new product'}</h2>
                                </div>

                                {/*===== Form-Body =====*/}
                                <div className="form_body">
                                    <div className="input_box">
                                        <input
                                            type="brand"
                                            name="brand"
                                            className="input_field"
                                            value={inputValues.brand || ''}
                                            onChange={handleChangeInputValues}
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
                                            onChange={handleChangeInputValues}
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
                                            onChange={handleChangeInputValues}
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
                                            onChange={handleChangeInputValues}
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
                                            onChange={handleChangeInputValues}
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
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <label className="input_label">Connectivity</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="originalPrice"
                                            name="originalPrice"
                                            className="input_field"
                                            value={inputValues.originalPrice || ''}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <label className="input_label">OriginalPrice</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="finalPrice"
                                            name="finalPrice"
                                            className="input_field"
                                            value={inputValues.finalPrice || ''}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <label className="input_label">FinalPrice</label>
                                    </div>
                                    <div className="input_box">
                                        <input
                                            type="quantity"
                                            name="quantity"
                                            className="input_field"
                                            value={inputValues.quantity || ''}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <label className="input_label">Quantity</label>
                                    </div>

                                    {isError && <label style={{color: 'red'}}>{errorMessage}</label>}

                                    <div className="actions">
                                        <button
                                            type="submit"
                                            className="btn save_btn"
                                        >
                                            Save
                                        </button>
                                        <button
                                            type="button"
                                            className="btn cancel_btn"
                                            onClick={() => toggleProductForm(false)}
                                        >
                                            Cancel
                                        </button>
                                    </div>

                                </div>

                                {/*===== Form-Close-Btn =====*/}
                                <div
                                    className="close_btn"
                                    title="Close"
                                    onClick={() => toggleProductForm(false)}
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