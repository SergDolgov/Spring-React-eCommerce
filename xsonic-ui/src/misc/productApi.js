import axios from 'axios'
import { config } from '../Constants'
import { parseJwt } from './helpers'

export const productApi = {
  authenticate,
  signup,
  numberOfUsers,
  numberOfProducts,
  getUsers,
  deleteUser,
  getProduct,
  getProducts,
  getProductsCart,
  getBrands,
  deleteProduct,
  addProduct
}

function authenticate(username, password) {
  return instance.post('/auth/authenticate', { username, password }, {
    headers: { 'Content-type': 'application/json' }
  })
}

function signup(user) {
  return instance.post('/auth/signup', user, {
    headers: { 'Content-type': 'application/json' }
  })
}

function numberOfUsers() {
  return instance.get('/public/numberOfUsers')
}

function numberOfProducts() {
  return instance.get('/public/numberOfProducts')
}

function getUsers(user, username) {
  const url = username ? `/api/v1/users/${username}` : '/api/v1/users'
  return instance.get(url, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function updateUser(user) {
  return instance.post(`/api/v1/users/${user.id}`, user, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
      }
  })
}

function deleteUser(user, username) {
  return instance.delete(`/api/v1/users/${username}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getBrand(user, id) {
  return instance.get(`/api/v1/brands/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getBrands(user) {
  return instance.get(`/api/v1/brands`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getProduct(user, id) {
  return instance.get(`/api/v1/products/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getProducts(user, text) {
  const url = text ? `/api/v1/products?text=${text}` : '/api/v1/products'
  return instance.get(url)
}

function getProductsCart(user, productsIds) {
  return instance.post('/api/v1/products/cart', productsIds, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function deleteProduct(user, id) {
  return instance.delete(`/api/v1/products/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function addProduct(user, product) {
  return instance.post('/api/v1/products', product, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function addProductToCart(user, product) {
  return instance.post('/api/v1/cart', product, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function getOrder(user, id) {
  return instance.get(`/api/v1/products/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL
})

instance.interceptors.request.use(function (config) {
  // If token is expired, redirect user to login
  if (config.headers.Authorization) {
    const token = config.headers.Authorization.split(' ')[1]
    const data = parseJwt(token)

    if (Date.now() > data.exp * 1000) {
     window.location.href = "/login"
    }

  }
  return config
}, function (error) {
  return Promise.reject(error)
})

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.accessToken}`
}