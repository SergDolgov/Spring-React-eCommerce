import React, { useContext } from 'react';
import { BsExclamationCircle } from 'react-icons/bs';
import { Button, Image, Table } from 'semantic-ui-react'
import useDocTitle from '../hooks/useDocTitle';
import FilterBar from '../components/filters/FilterBar';
//import ProductCard from '../components/product/ProductCard';
import Services from '../components/common/Services';
import filtersContext from '../contexts/filters/filtersContext';
import EmptyView from '../components/common/EmptyView';


const AdminProducts = () => {

    useDocTitle('Admin Products');

    const { allProducts } = useContext(filtersContext);

    // handling Delete-product
    const handleDeleteProduct = (productId) => {
        //const product = { ...props };
        //deleteProduct(product);
    };

    return (
        <>
            <section id="admin_products" className="section">
                <FilterBar />

                <div className="container">
                    {
                        allProducts.length ? (
                            <div className="wrapper products_table_wrapper">
                                {
                                    allProducts.map(item => (
                                        <Table.Row key={item.id}>
                                          <Table.Cell collapsing>
                            <div className="products_table_btn">
                                <button
                                    type="button"
                                    circular
                                    icon='trash'
                                    //className="btn"
                                    onClick={handleDeleteProduct}
                                >+
                                </button>
                            </div>
                                            <Button
                                              circular
                                              color='red'
                                              size='small'
                                              icon='trash'
                                              onClick={() => handleDeleteProduct(item.id)}
                                            />
                                          </Table.Cell>
                                          <Table.Cell>
                                            {// item.images ?
                                            //<Image src={item.images[0]} size='tiny' bordered rounded /> :
                                            <Image src='/images/product-poster.jpg' size='tiny' bordered rounded />
                                            }
                                          </Table.Cell>
                                          <Table.Cell>{item.id}</Table.Cell>
                                          <Table.Cell>{item.category}</Table.Cell>
                                          <Table.Cell>{item.title}</Table.Cell>
                                          <Table.Cell>{item.info}</Table.Cell>
                                          <Table.Cell>{item.ratings}</Table.Cell>
                                          <Table.Cell>{item.rateCount}</Table.Cell>
                                          <Table.Cell>{item.originalPrice}</Table.Cell>
                                          <Table.Cell>{item.finalPrice}</Table.Cell>
                                        </Table.Row>
                                    ))
                                }
                            </div>
                        ) : (
                            <EmptyView
                                icon={<BsExclamationCircle />}
                                msg="No Results Found"
                            />
                        )
                    }
                </div>
            </section>

            <Services />
        </>
    );
};

export default AdminProducts;