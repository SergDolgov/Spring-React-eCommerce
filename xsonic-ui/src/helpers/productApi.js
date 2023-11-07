import axios from 'axios'
import { config } from '../Constants'
import { parseJwt } from './utils'

export const productApi = {
  login, registration,
  getUsers,  updateUser,  deleteUser,
  getProduct,  getProducts,  addProduct,  updateProduct,  deleteProduct,  getAdminProducts,
  getOrders,  getOrder,  postOrder
 }

function login(user) {
  return instance.post('/api/v1/auth/login', user, {
    headers: { 'Content-type': 'application/json' }
  })
}

function registration(user) {
  return instance.post('/api/v1/registration', user, {
    headers: { 'Content-type': 'application/json' }
  })
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

function getProduct(id) {
  return instance.get(`/api/v1/products/${id}`)
}

function getProducts(text) {
  const url = text ? `/api/v1/products?text=${text}` : '/api/v1/products'
  return instance.get(url)
}

function deleteProduct(user, id) {
  return instance.delete(`/api/v1/admin/delete/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function addProduct(user, product) {
  return instance.post('/api/v1/admin/add', product, {
    headers: {
      'Content-type': 'multipart/form-data',//'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function updateProduct(user, product) {
  return instance.post('/api/v1/admin/update', product, {
    headers: {
      'Content-type': 'multipart/form-data',//'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function getAdminProducts(user) {
  return instance.get(`/api/v1/admin/get`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getOrder(user, id) {
  return instance.get(`/api/v1/order/${id}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getOrders(user) {
  return instance.get(`/api/v1/orders`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function postOrder(user, order) {
  return instance.post(`/api/v1/order`, order, {
    'Content-type': 'application/json',
    headers: { 'Authorization': bearerAuth(user) }
  })
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL
})

instance.interceptors.request.use(function (config) {
  // If token is expired, redirect start page
  if (config.headers.Authorization) {
    const token = config.headers.Authorization.split(' ')[1]
    const data = parseJwt(token)

    if (Date.now() > data.exp * 1000) {
     window.location.href = '/'
    }

  }
  return config
}, function (error) {
  return Promise.reject(error)
})

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.token}`
}