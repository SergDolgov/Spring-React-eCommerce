import React, { useContext, useRef, useEffect, useState } from 'react';
import useProductForm from '../../hooks/useProductForm';
import commonContext from '../../contexts/common/commonContext';
import useOutsideClose from '../../hooks/useOutsideClose';
import useScrollDisable from '../../hooks/useScrollDisable';
import useActive from '../../hooks/useActive';

const ProductForm = ({selectedProduct}) => {

    const { isProductFormOpen, toggleProductForm } = useContext(commonContext);
    const { inputValues, setInputValues, handleChangeInputValues, file, handleUpload, handleFormSubmit, isError, errorMessage } = useProductForm();

    const [images, setImages] = useState([]);

    const { handleActive, activeClass } = useActive(0);

    useEffect(() => {
        setInputValues(selectedProduct);
        getImages();
    }, [setInputValues, selectedProduct]);

    const formRef = useRef();

    useOutsideClose(formRef, () => {
        toggleProductForm(false);
    });

    useScrollDisable(isProductFormOpen);

//    useEffect(() => {
//        getImages();
//    },[]);

    const getImages = () => {
        const basePath = '/images/products/';
        const imageArray = [];

        for (let i = 1; i <= 4; i++) {
            const newImagePath = selectedProduct.filename ? `${basePath}${selectedProduct.filename.replace('.png', `-${i}.png`)}`: '/images/empty-image.png';
            imageArray.push(newImagePath);
        }
        setImages(imageArray)
    };

     const handleUploadImage = (event, i) => {
         const file = event.target.files[0];
         const reader = new FileReader();
         reader.onloadend = () => {
             const imageUrl = reader.result;
             setImages(prevImages => {
                 const updatedImages = [...prevImages];
                 updatedImages[i] = imageUrl;
                 return updatedImages;
             });
         };
         if (file) {
             reader.readAsDataURL(file);
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