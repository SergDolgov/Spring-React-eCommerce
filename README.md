## XSonic | Spring Boot and React eCommerce Web App

### [Demo Link](https://xsonic.netlify.app/) 🔗

Audio Store eCommerce Website, developed using **Spring Boot** and **React JS**. <br/>
State management using Context API.
<br/>

## Used Technologies:

* Back-end: Spring (Boot, Data, Security), JPA / Hibernate, PostgreSQL, JUnit, Mockito
* Front-end: React JS, React Router, React Swiper
* Security: JWT, OAuth2 Google, Facebook, Github
* REST API
* Server Build: Docker, Gradle
* Client Build: npm, yarn, webpack

## Features

* Authentication with JWT and Email validation.
* Authentication with Google, Facebook or Github
* Customers can search for the product according to the specified criteria.
* Customers can add and delete products from the shopping cart.
* Customers can order the products in the shopping cart.
* Customers can change their password and view their orders.
* Admin can add or modify a product.
* Admin can change the data of any user.
* Admin can view orders of all users.
* ...and much more


Register in gmail
* Configure reCAPTCHA: [link](https://www.google.com/recaptcha/admin#list), [guide](https://developers.google.com/recaptcha/docs/verify), [video guide (RUS)](https://youtu.be/7cDpbAbhyjc?t=212)
* Add  reCAPTCHA key to the application.properties file: [link](https://i.ibb.co/nDTP8H5/prop-recaptcha.png) and to [link](https://github.com/merikbest/ecommerce-spring-reactjs/blob/4f74f86500ab9363c04a18412dd432bd913e0477/frontend/src/pages/Registration/Registration.tsx#L134)
* Add gmail account and password to the application.properties file: [link](https://i.ibb.co/0tRr1Gy/props-gmail.png)
* Go to [link](https://myaccount.google.com/u/2/lesssecureapps) (important) and change to: “Allow less secure apps: ON”
* Configure OAuth2: [link](https://console.cloud.google.com/apis/credentials), [guide](https://spring.io/guides/tutorials/spring-boot-oauth2/), [video guide (RUS)](https://www.youtube.com/watch?v=-ohlXEJeRX8&ab_channel=letsCode)
* Add OAuth2 properties to the application.properties file: [link](https://i.ibb.co/YpH4V3m/oauth2-props.png)
* Install node.js and npm: [link](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
* Now you can run EcommerceApplication (port 8080) and open terminal in client directory and type: npm start
* Navigate to http://localhost:3000

## Available Scripts:

#### `npm start`

#### `npm test`

#### `npm run build`

#### `npm run eject`

## Swagger Documentation
http://localhost:8080/swagger-ui.html

## Screenshots

Menu page  |  Product page
:------------------------:|:-------------------------:
![Menu page](https://i.ibb.co/VT4RzYj/1menu.jpg)  |  ![Product page](https://i.ibb.co/HtnKp0W/2-Product-page.jpg)

Cart  |  Ordering
:------------------------:|:-------------------------:
![Email template](https://i.ibb.co/8Y8bfSG/3-Cart.jpg)  |  ![List of users](https://i.ibb.co/tLmY8y2/4-Ordering.jpg)

Email template  |  List of orders
:------------------------:|:-------------------------:
![Email template](https://i.ibb.co/bmKTLPJ/email-template.jpg)  |  ![List of users](https://i.ibb.co/pLTyF25/6-List-of-orders.jpg)

User profile page  |  Add perfume page
:------------------------:|:-------------------------:
![User profile page](https://i.ibb.co/qx1Csc8/7-User-profile-page.jpg)  |  ![Add perfume page](https://i.ibb.co/XbsJPQH/8-Add-perfume-page.jpg)

Edit perfume list  |  Edit perfume page
:------------------------:|:-------------------------:
![Edit perfume list](https://i.ibb.co/HFb9wfR/9-Edit-perfume-list.jpg)  |  ![Edit perfume page](https://i.ibb.co/jH8R8xL/10-Edit-perfume-page.jpg)

## License:

This project is licensed under the  **GPL-3.0 License** - see the [LICENSE](LICENSE.md) file for details.
