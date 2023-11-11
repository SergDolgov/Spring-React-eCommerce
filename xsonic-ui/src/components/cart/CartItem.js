import React, { useContext, useState, useCallback, useEffect } from 'react';
import { TbTrash } from 'react-icons/tb';
import { Link } from 'react-router-dom';
import { displayMoney } from '../../helpers/utils';
import cartContext from '../../contexts/cart/cartContext';
import QuantityBox from '../common/QuantityBox';


const CartItem = (props) => {

    const { id, filename, title, info, finalPrice, originalPrice, quantity, path } = props;

    const { removeItem } = useContext(cartContext);

    const newPrice = displayMoney(finalPrice);
    const oldPrice = displayMoney(originalPrice);

    const [images, setImages] = useState([]);

    const getImages = useCallback(() => {
        const basePath = '/images/products/';
        const imageArray = [];

        for (let i = 1; i <= 4; i++) {
            const newImagePath = filename && filename !== 'empty-image.png' ? `${basePath}${filename.replace('.png', `-${i}.png`)}`: '/images/empty-image.png';
            imageArray.push(newImagePath);
        }
        setImages(imageArray)
    }, [filename]);

    useEffect(() => {
        getImages();
    }, [getImages]);

    return (
        <>
            <div className="cart_item">
                <figure className="cart_item_img">
                    <Link to={`${path}${id}`}>
                        <img src={images[0]} alt="product-img" />
                    </Link>
                </figure>
                <div className="cart_item_info">
                    <div className="cart_item_head">
                        <h4 className="cart_item_title">
                            <Link to={`/product-details/${id}`}>{title} {info}</Link>
                        </h4>
                        <div className="cart_item_del">
                            <span onClick={() => removeItem(id)}>
                                <TbTrash />
                            </span>
                            <div className="tooltip">Remove Item</div>
                        </div>
                    </div>

                    <h2 className="cart_item_price">
                        {newPrice} &nbsp;
                        <small><del>{oldPrice}</del></small>
                    </h2>

                    <QuantityBox itemId={id} itemQuantity={quantity} />
                </div>
            </div>
        </>
    );
};

export default CartItem;