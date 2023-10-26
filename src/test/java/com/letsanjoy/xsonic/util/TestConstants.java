package com.letsanjoy.xsonic.util;

public class TestConstants {

    public static final Integer USER_ID = 122;
    public static final String USER_EMAIL = "test123@test.com";
    public static final String USER_PASSWORD = "admin123";
    public static final String ROLE_USER = "USER";
    public static final String USER_PROVIDER = "LOCAL";

    public static final String USER_PASSWORD_RESET_CODE = "3f9bcdb0-2241-4c34-803e-598b497d571f";
    public static final String USER_ACTIVATION_CODE = "8e97dc37-2cf5-47e2-98e0";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE2MjExODI4MTcsImV4cCI6MjIyNTk4MjgxN30.5GxJbuta48cVrn9EWYKrSQruk9jm06fpBu87JxTY_uk";

    public static final Integer USER2_ID = 126;
    public static final String USER2_EMAIL = "helloworld@test.com";
    public static final String USER2_NAME = "John2";

    public static final String EMAIL_FAILURE = "1t2e3st123@test.com";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final Double TOTAL_PRICE = 56.0;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String CITY = "New York";
    public static final String ADDRESS = "Wall Street 1";
    public static final String ORDER_EMAIL = "test123@test.com";
    public static final String PHONE_NUMBER = "1234567890";
    public static final Integer POST_INDEX = 1234567890;

    public static final String BRAND_CREED = "Creed";
    public static final String BRAND_CHANEL = "Chanel";
    public static final String TITLE = "Chanel N5";
    public static final Integer RATE_COUNT = 1234;
    public static final String CATEGORY = "France";
    public static final String CONNECTIVITY = "female";
    public static final Integer FINAL_PRICE = 1234;
    public static final Integer ORIGINAL_PRICE = 1524;
    public static final Integer QUANTITY = 1;
    public static final Integer PRICE = 192;
    public static final String INFO = "200";
    public static final String TYPE = "Eau de parfum";

    public static final String FILE_NAME = "Chanel N5.jpg";
    public static final String FILE_PATH = "src/test/resources/empty.jpg";

    public static final String GRAPHQL_QUERY_USERS = "{ users { id email password firstName lastName city " +
            "address phoneNumber postIndex activationCode passwordResetCode active provider roles } }";
    public static final String GRAPHQL_QUERY_USER = "{ user(id: 122) { id email password firstName lastName city " +
            "address phoneNumber postIndex activationCode passwordResetCode active provider roles } }";
    public static final String GRAPHQL_QUERY_ORDERS = "{ orders { id totalPrice date firstName lastName city address " +
            "email phoneNumber postIndex orderItems { id amount quantity product { id title brand price filename } } } }";
    public static final String GRAPHQL_QUERY_ORDERS_BY_EMAIL = "{ ordersByEmail(email: \"test123@test.com\") { id totalPrice date firstName lastName city address " +
            "email phoneNumber postIndex orderItems { id amount quantity product { id title brand price filename } } } }";
    public static final String GRAPHQL_QUERY_PRODUCTS_BY_IDS = "{ productsIds(ids: [3,4,5]) { id title brand price } }";
    public static final String GRAPHQL_QUERY_PRODUCTS = "{ products { id title brand price filename } }";
    public static final String GRAPHQL_QUERY_PRODUCT = "{ product(id: 1) { id title brand price } }";
}
