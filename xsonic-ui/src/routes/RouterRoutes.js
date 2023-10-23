import React from 'react';
import { Routes, Route } from 'react-router';
import useScrollRestore from '../hooks/useScrollRestore';
import OAuth2Redirect from '../hooks/OAuth2Redirect'
import AllProducts from '../pages/AllProducts';
import AdminProducts from '../pages/AdminProducts';
import Cart from '../pages/Cart';
import Home from '../pages/Home';
import ProductDetails from '../pages/ProductDetails';
import ErrorPage from '../pages/ErrorPage';

const RouterRoutes = () => {

    useScrollRestore();

    return (
        <>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path='/oauth2/redirect' element={<OAuth2Redirect />} />
                <Route path="/cart" element={<Cart />} />
                <Route path="/all-products" element={<AllProducts />} />
                <Route path="/product-details/:productId" element={<ProductDetails />} />
                <Route path="*" element={<ErrorPage />} />
                <Route path="/admin/products" element={<AdminProducts />} />
            </Routes>
        </>
    );
};

export default RouterRoutes;