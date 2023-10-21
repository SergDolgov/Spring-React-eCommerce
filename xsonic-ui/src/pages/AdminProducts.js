import React, { useContext, useState } from 'react';
import { BsExclamationCircle, BsPencilSquare, BsTrash, BsPlusSquare } from 'react-icons/bs';
import useDocTitle from '../hooks/useDocTitle';
import FilterBar from '../components/filters/FilterBar';
import ProductCard from '../components/product/ProductCard';
import Services from '../components/common/Services';
import filtersContext from '../contexts/filters/filtersContext';
import commonContext from '../contexts/common/commonContext';
import EmptyView from '../components/common/EmptyView';
import ProductForm from '../components/form/ProductForm';

const AllProducts = () => {
    useDocTitle('All Products');

    const { formProductInfo, setFormProductInfo, toggleFormProduct, getUser } = useContext(commonContext);

    const { allProducts } = useContext(filtersContext);
    const [selectedProduct, setSelectedProduct] = useState(null);

    const handleAddProduct = () => {
        setFormProductInfo(null);
        toggleFormProduct(true)
    };

    const handleEditProduct = () => {
        if (selectedProduct != null) {
            setFormProductInfo(selectedProduct);
            toggleFormProduct(true)
        }
    };

    const handleDeleteProduct = () => {
        // Обработчик удаления продукта
    };

    return (
        <>
            <section id="admin_products" className="section">
                <FilterBar />
                <div className="container">
                    <div className="actions">
                        <BsPlusSquare onClick={handleAddProduct} className="action-icon" />
                        <BsPencilSquare onClick={handleEditProduct} className="action-icon" />
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
                                    <th>Price</th>
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
                                        <td>${item.finalPrice}</td>
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
            <ProductForm />
        </>
    );
};

export default AllProducts;
