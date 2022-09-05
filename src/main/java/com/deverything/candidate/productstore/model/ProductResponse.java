package com.deverything.candidate.productstore.model;

import java.util.List;

public class ProductResponse {
    int statusCode;
    List<Product> products;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
