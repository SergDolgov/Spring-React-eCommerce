import React, { useContext, useRef, useEffect, useState, useCallback } from 'react';
import useProductForm from '../../hooks/useProductForm';
import commonContext from '../../contexts/common/commonContext';
import useOutsideClose from '../../hooks/useOutsideClose';
import useScrollDisable from '../../hooks/useScrollDisable';
import useActive from '../../hooks/useActive';
import ProductFormField from './ProductFormField';

const ProductForm = ({selectedProduct}) => {

    const { isProductFormOpen, toggleProductForm } = useContext(commonContext);
    const { inputValues, setInputValues, handleChangeInputValues, handleUpload, handleFormSubmit, isError, errorMessage } = useProductForm();
    const { activeClass } = useActive(0);

    const [images, setImages] = useState([]);

    const formRef = useRef();

    const getImages = useCallback(() => {
        const basePath = '/images/products/';
        const imageArray = [];

        for (let i = 1; i <= 4; i++) {
            const newImagePath = selectedProduct.filename && selectedProduct.filename !== 'empty-image.png' ? `${basePath}${selectedProduct.filename.replace('.png', `-${i}.png`)}`: '/images/empty-image.png';
            imageArray.push(newImagePath);
        }
        setImages(imageArray)
    }, [selectedProduct]);

    useEffect(() => {
        setInputValues(selectedProduct);
        getImages();
    }, [setInputValues, selectedProduct, getImages]);

    useOutsideClose(formRef, () => {
        toggleProductForm(false);
    });

    useScrollDisable(isProductFormOpen);

     const handleUploadImage = (event, i) => {
         const fileImage = event.target.files[0];
         const reader = new FileReader();
         reader.onloadend = () => {
             const imageUrl = reader.result;
             setImages(prevImages => {
                 const updatedImages = [...prevImages];
                 updatedImages[i] = imageUrl;
                 return updatedImages;
             });
         };
         if (fileImage) {
             reader.readAsDataURL(fileImage);
             handleUpload(event)
         }
     };


    return (
        <>
            {
                isProductFormOpen && (
                    <div className="backdrop">
                        <div className="modal_centered">
                            <form id="product_form" ref={formRef} onSubmit={handleFormSubmit}>

                                {/*===== Form-Header =====*/}
                                <div className="product_form_head">
                                    <h2>{inputValues.id ? 'Update product': 'Add new product'}</h2>
                                </div>

                                {/*===== Form-Body =====*/}
                                <div className="product_form_body">

                                    {/*===== image_block =====*/}
                                    <div className="image_block">
                                        <div className="image_details_tabs">
                                            {
                                                images.map((img, i) => (
                                                    <div  className={`tabs_item ${activeClass(i)}`} key={i}>
                                                        <img src={img} alt="product-img" />
                                                         <label htmlFor={`file-upload-${i}`} className="file-upload-label">
                                                            <span>...</span>
                                                            <input
                                                                type="file"
                                                                id={`file-upload-${i}`}
                                                                onChange={(event) => handleUploadImage(event, i)}
                                                                accept="image/*"
                                                            />
                                                        </label>
                                                    </div>
                                                ))
                                            }
                                        </div>
                                    </div>

                                   {/*===== form_fields == ===*/}
                                    <div className="product_form_fields">
                                        <ProductFormField
                                            type="brand"
                                            name="brand"
                                            label="Brand"
                                            value={inputValues.brand}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="title"
                                            name="title"
                                            label="Title"
                                            value={inputValues.title}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="info"
                                            name="info"
                                            label="Info"
                                            value={inputValues.info}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="category"
                                            name="category"
                                            label="Category"
                                            value={inputValues.category}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="type"
                                            name="type"
                                            label="Type"
                                            value={inputValues.type}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="connectivity"
                                            name="connectivity"
                                            label="Connectivity"
                                            value={inputValues.connectivity}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="originalPrice"
                                            name="originalPrice"
                                            label="OriginalPrice"
                                            value={inputValues.originalPrice}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="finalPrice"
                                            name="finalPrice"
                                            label="FinalPrice"
                                            value={inputValues.finalPrice}
                                            onChange={handleChangeInputValues}
                                            required
                                        />
                                        <ProductFormField
                                            type="quantity"
                                            name="quantity"
                                            label="Quantity"
                                            value={inputValues.quantity}
                                            onChange={handleChangeInputValues}
                                            required
                                        />

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