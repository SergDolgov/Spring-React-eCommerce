import { CommonProvider } from './contexts/common/commonContext';
import { AuthProvider } from './contexts/authContext';
import { CartProvider } from './contexts/cart/cartContext';
import Header from './components/common/Header';
import RouterRoutes from './routes/RouterRoutes';
import Footer from './components/common/Footer';
import BackTop from './components/common/BackTop';
import { FiltersProvider } from './contexts/filters/filtersContext';


const App = () => {
  return (
    <>
    <AuthProvider>
      <CommonProvider>
        <FiltersProvider>
          <CartProvider>
             <Header />
             <RouterRoutes />
             <Footer />
             <BackTop />
          </CartProvider>
         </FiltersProvider>
       </CommonProvider>
     </AuthProvider>
    </>
  );
};

export default App;
