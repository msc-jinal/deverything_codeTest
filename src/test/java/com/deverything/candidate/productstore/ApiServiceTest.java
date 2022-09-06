package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    ApiService apiService;

    @Test
    public void testGetProducts(){
        assertNotNull(apiService.getProducts(),"Unable to get All Products from API");
    }

    @Test
    public void testGetBoxes(){
        assertNotNull(apiService.getBoxes(),"Unable to get All Boxes from API");
    }

    @Test
    public void testGetProductByPrice(){
        List<Product> productList = apiService.getProductsByPrice(300);
        assertEquals (4, productList.size(),"Did not get expected number of products");
    }

    @Test
    public void testGetProductDimension(){
        ProductDimensionResponse response =  apiService.getProductDimensions(3);
        assertEquals(150,response.getWidth());
        assertEquals(150,response.getHeight());

        response =  apiService.getProductDimensions(7);
        assertEquals(225,response.getWidth());
        assertEquals(230,response.getHeight());
    }

    @Test
    public void testCheckoutSuccess(){
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(7);
        CheckoutRequest checkoutRequest = new CheckoutRequest(3,list);
        CheckoutResponse checkoutResponse = apiService.checkout(checkoutRequest);
        assertEquals(200,checkoutResponse.getStatusCode(),"Unable to process checkout");
    }

    @Test
    public void testCheckoutFail(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        CheckoutRequest checkoutRequest = new CheckoutRequest(3,list);
        CheckoutResponse checkoutResponse = apiService.checkout(checkoutRequest);
        assertNull(checkoutResponse,"Unable to process checkout");
    }

    @Test
    public void testFindSuitableBox(){
        List<Integer> productIds = new ArrayList<>();
        productIds.add(3);
        productIds.add(7);
        Box box = apiService.findSuitableBox (productIds);
        assertNotNull(box,"Did not found suitable box");
        assertEquals(5, box.getId(),"Did not found suitable box");
    }

    @Test
    public void testAllTheThings(){
        /*
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
        */
    }
}