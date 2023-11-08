import React, { useContext, useEffect, useState, useMemo } from 'react';
import { Link, useNavigate  } from 'react-router-dom';
import { AiOutlineSearch, AiOutlineShoppingCart, AiOutlineUser, AiOutlineSmile } from 'react-icons/ai';
import { dropdownMenuUser, dropdownMenuAdmin } from '../../data/headerData';
import commonContext from '../../contexts/common/commonContext';
import cartContext from '../../contexts/cart/cartContext';
import AccountForm from '../form/AccountForm';
import SearchBar from './SearchBar';

const Header = () => {

    const navigate = useNavigate();

    const { userName, userRole, toggleForm, toggleSearch, userLogout } = useContext(commonContext);
    const { cartItems } = useContext(cartContext);
    const [isSticky, setIsSticky] = useState(false);

    const isUserRole = useMemo(() => userRole === 'USER', [userRole]);
    const isAdminRole = useMemo(() => userRole === 'ADMIN', [userRole]);

    const dropdownMenu = useMemo(() => {
        const menu = isUserRole ? dropdownMenuUser : isAdminRole ? dropdownMenuAdmin : [];
        return menu.map((item) => {
            const { id, link, path } = item;
            return (
                <li key={id}>
                    <Link to={path}>{link}</Link>
                </li>
            );
        });
    }, [isUserRole, isAdminRole]);


    const handleUserLogout = ()=>{
        userLogout()
        navigate('/')
    }

    useEffect(() => {
        if (isAdminRole) {
            navigate('/admin')
        }
    }, [isAdminRole]);


    // handle the sticky-header
    useEffect(() => {
        const handleIsSticky = () => window.scrollY >= 50 ? setIsSticky(true) : setIsSticky(false);

        window.addEventListener('scroll', handleIsSticky);

        return () => {
            window.removeEventListener('scroll', handleIsSticky);
        };
    }, [isSticky]);


    const cartQuantity = cartItems.length;


    return (
        <>
            <header id="header" className={isSticky ? 'sticky' : ''}>
                <div className="container">
                    <div className="navbar">
                        <h2 className="nav_logo">
                            <Link to="/">XSonic</Link>
                        </h2>
                        <nav className="nav_actions">
                            <div className="search_action">
                                <span onClick={() => toggleSearch(true)}>
                                    <AiOutlineSearch />
                                </span>
                                <div className="tooltip">Search</div>
                            </div>

                            <div className="cart_action">
                                <Link to="/cart">
                                    <AiOutlineShoppingCart />
                                    {
                                        cartQuantity > 0 && (
                                            <span className="badge">{cartQuantity}</span>
                                        )
                                    }
                                </Link>
                                <div className="tooltip">Cart</div>
                            </div>

                            <div className="user_action">
                                <span>
                                    {userName ? <AiOutlineSmile/> : <AiOutlineUser/>}
                                </span>
                                <div className="dropdown_menu">
                                    <h4>Hello! {userName && <Link to="*">&nbsp;{userName}</Link>}</h4>
                                    {userName && <p>Access account and manage orders</p>}
                                    {
                                        !userName ? (
                                            <button
                                                type="button"
                                                onClick={() => toggleForm(true)}
                                            >
                                                Login / Signup
                                            </button>
                                        ):(
                                            <button
                                                type="button"
                                                onClick={() => handleUserLogout()}
                                            >
                                                Logout
                                            </button>
                                        )
                                    }
                                    {userName && <div className="separator"></div>}
                                    <ul>
                                        {userName && dropdownMenu}
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </div>
                </div>
            </header>

            <SearchBar />
            <AccountForm />
        </>
    );
};

export default Header;