import React from 'react'
import { Navigate } from 'react-router-dom'
import { authContext } from '../contexts/authContext'

function PrivateRoute({ children }) {
  const { userIsAuthenticated } = authContext()
  return userIsAuthenticated() ? children : <Navigate to="/login" />
}

export default PrivateRoute