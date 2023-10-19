import React, { createContext, useContext, useState, useEffect } from 'react'
import { productApi } from '../misc/productApi'
import { parseJwt, handleLogError } from '../misc/helpers'
 
const ProductsContext = createContext()

function ProductsProvider({ children }) {

  const [products1, setProducts1] = useState([])
  const [isProductsLoading, setIsProductsLoading] = useState(false)

  useEffect(() => {
    //handleGetProducts()
  }, [])

  const getProducts = async () => {
    setIsProductsLoading(true)
    try {
      const response = await productApi.getProducts()
      setProducts1(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsProductsLoading(false)
    }
  }

  const contextValue = {
    useProducts,
  }

  return (
    <ProductsContext.Provider value={contextValue}>
      {children}
    </ProductsContext.Provider>
  )
}

export default ProductsContext

export function useProducts() {
  return useContext(ProductsContext)
}

export { ProductsProvider }
