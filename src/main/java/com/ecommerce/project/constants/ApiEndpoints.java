package com.ecommerce.project.constants;

public class ApiEndpoints {
    public static final String API = "/api";
    public static final String PUBLIC = "/public";
    public static final String PRIVATE = "/private";
    public static final String ADMIN = "/admin";

    // CATEGORIES
    public static final String PUBLIC_CATEGORIES_URL = API + PUBLIC + "/categories";
    public static final String ADMIN_CATEGORIES_URL = API + ADMIN + "/categories";

    // PRODUCTS
    public static final String PUBLIC_PRODUCTS_URL = API + PUBLIC + "/products";
    public static final String ADMIN_PRODUCTS_URL = API + ADMIN + "/products";
    public static final String ADMIN_CREATE_PRODUCT_URL = ADMIN_CATEGORIES_URL + "/{categoryId}/product";

}
