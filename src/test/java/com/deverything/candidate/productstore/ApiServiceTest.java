package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.model.CheckoutRequest;
import com.deverything.candidate.productstore.model.CheckoutResponse;
import com.deverything.candidate.productstore.model.Product;
import com.deverything.candidate.productstore.model.ProductDimensionResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    ApiService api;

    @Test
    public void testGetProducts(){
        List<Product> productList = api.getProducts();
        assertNotNull(productList,"Unable to get All Products from API");
    }

    @Test
    public void testGetBoxes(){
        assertNotNull(api.getBoxes(),"Unable to get All Boxes from API");
    }

    @Test
    public void testGetProductByPrice(){
        List<Product> productList = api.getProductsByPrice(300);
        assertNotNull(productList,"Unable to get All Products from API");
        System.out.println(productList.size());
    }

    @Test
    public void testGetProductDimension(){
        ProductDimensionResponse response =  api.getProductDimensions(7);
        assertEquals(225,response.getWidth());
        assertEquals(230,response.getHeight());
    }


    @Test
    public void testCheckoutSuccess(){
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(7);
        CheckoutRequest checkoutRequest = new CheckoutRequest(3,list);
        CheckoutResponse checkoutResponse = api.checkout(checkoutRequest);
        assertEquals(200,checkoutResponse.getStatusCode(),"Unable to process checkout");
    }

    @Test
    public void testCheckoutFail(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        CheckoutRequest checkoutRequest = new CheckoutRequest(3,list);
        CheckoutResponse checkoutResponse = api.checkout(checkoutRequest);
        assertNull(checkoutResponse,"Unable to process checkout");
    }

    @Test
    public void testAllTheThings(){
        System.out.println("Let's get all products from the API:");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Let's list all products with a price higher then 300");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Let's get product dimensions for products with id 3 and 7");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Get all boxes and choose the best one that fits both the products 3 and 7 in a single box");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Now we place the order using the checkout in the API");
        System.out.println("YOUR-RESULT-HERE");
    }
}