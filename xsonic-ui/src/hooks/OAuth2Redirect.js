import React, { useContext, useEffect, useState } from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import commonContext from '../contexts/common/commonContext'
import { parseJwt } from '../misc/helpers'

function OAuth2Redirect() {
  const { userLogin } = useContext(commonContext)
  const [redirectTo, setRedirectTo] = useState('/')

  const location = useLocation()

  useEffect(() => {
    const accessToken = extractUrlParameter('token')
    if (accessToken) {
      handleLogin(accessToken)
      const redirect = '/'
      setRedirectTo(redirect)
    }
  }, [])

  const extractUrlParameter = (key) => {
    return new URLSearchParams(location.search).get(key)
  }

  const handleLogin = (accessToken) => {
    const data = parseJwt(accessToken)
    const user = { data, accessToken }

    userLogin(user)
  };

  return <Navigate to={redirectTo} />
}

export default OAuth2Redirect