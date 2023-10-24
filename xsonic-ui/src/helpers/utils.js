import { config } from '../Constants'

export function parseJwt(token) {
  if (!token) { return }
  const base64Url = token.split('.')[1]
  const base64 = base64Url.replace('-', '+').replace('_', '/')
  return JSON.parse(window.atob(base64))
}

export function getSocialLoginUrl(name) {
  return `${config.url.API_BASE_URL}/oauth2/authorization/${name}?redirect_uri=${config.url.OAUTH2_REDIRECT_URI}`
}

export const handleLogError = (error) => {
  if (error.response) {
    console.log(error.response.data)
  } else if (error.request) {
    console.log(error.request)
  } else {
    console.log(error.message)
  }
}

// Display Money in EUR Format
export const displayMoney = (n) => {
    const numFormat = new Intl.NumberFormat('en-DE', {
        style: 'currency',
        currency: 'EUR',
    });

    return numFormat.format(n).split('.', 1);
};


// Calculate Discount Percentage
export const calculateDiscount = (discountedPrice, originalPrice) => {
    const discountedPercent = (discountedPrice / originalPrice) * 100;

    return Math.round(discountedPercent);
};


// Calculate Total Amount
export const calculateTotal = (arr) => {
    const total = arr.reduce((accum, val) => accum + val, 0);

    return total;
};