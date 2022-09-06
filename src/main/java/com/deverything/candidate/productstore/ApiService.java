package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.model.*;

import java.util.List;
import java.util.Optional;

public interface ApiService {

    /**
     * Should get a json list of products from API
     */
    public List<Product> getProducts();

    /**
     * Should get a json object back with width and heigh for a given productId
     */
    public ProductDimensionResponse getProductDimensions(int productId);

    /**
     * Should get a json object with a list of boxes from the API
     * @return
     */
    public List<Box> getBoxes();


    /**
     * Performs the checkout
     * @param checkoutRequest json object that contains the boxId and the list of products to checkout
     * @return the checkout summary object
     */
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest);

    /*
    Find all products with a price higher then 300
     */
    public List<Product> getProductsByPrice(int productPrice);

    public Box findSuitableBox(List<Integer> productIds);

}
