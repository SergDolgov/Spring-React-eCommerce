import React from 'react';
import { Link } from 'react-router-dom';
import { FaProductHunt, FaUser } from 'react-icons/fa';

const AdminPage = () => {
    return (
        <>
            <section id="admin_page" className="section">
                <div className="container">
                    <div className="admin_page_content">
                        <Link to="/admin/products">
                            <FaProductHunt size={150} />
                            <h1>Products</h1>
                        </Link>

                    </div>
                    <div className="admin_page_content">
                        <Link to="/admin/users">
                            <FaUser size={150} />
                            <h1>Users</h1>
                        </Link>
                    </div>
                </div>
            </section>
        </>
    );
};

export default AdminPage;
