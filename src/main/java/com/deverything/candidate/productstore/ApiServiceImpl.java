package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl implements ApiService{

    private static final String API_URL="https://api.deverything.se/candidate-product-store";
    private static final String PRODUCT_PATH="/products";
    private static final String PRODUCT_BY_ID_PATH="/products/%s";
    private static final String BOX_PATH="/boxes";
    private static final String CHECKOUT_PATH="/checkout";

    final static Logger logger = Logger.getLogger(ApiServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    private HttpHeaders setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("APIKEY","SUPERSECRETAPIKEY");
        headers.set("USER","JINAL");
        return headers;
    }

    @Override
    public List<Product> getProducts() {
        try{
            String requestUrl = API_URL+PRODUCT_PATH;
            HttpEntity <String> entity = new HttpEntity<String>(setHeaders());
            ProductResponse productResponse = restTemplate.exchange( requestUrl, HttpMethod.GET, entity, ProductResponse.class).getBody();
            return productResponse.getProducts();
        }catch(RestClientException re){
            logger.error("Error while fetching product list.",re);
            return null;
        }
    }

    @Override
    public ProductDimensionResponse getProductDimensions(int productId) {
        try{
            String requestUrl = String.format(API_URL+PRODUCT_BY_ID_PATH,productId);
            HttpEntity <String> entity = new HttpEntity<String>(setHeaders());
            ProductDimensionResponse productDimensionResponse = restTemplate.exchange(requestUrl,HttpMethod.GET,entity, ProductDimensionResponse.class).getBody();
            return productDimensionResponse;
        }catch(RestClientException re){
            logger.error("Error while fetching product dimension by product id.",re);
            return null;
        }
    }

    @Override
    public List<Box> getBoxes() {
        try{
            String requestUrl = API_URL+BOX_PATH;
            HttpEntity <String> entity = new HttpEntity<String>(setHeaders());
            BoxResponse boxResponse = restTemplate.exchange(requestUrl,HttpMethod.GET,entity,BoxResponse.class).getBody();
            return boxResponse.getBoxes();
        }catch(RestClientException re){
            logger.error("Error while fetching box list.",re);
            return null;
        }
    }

    @Override
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        try {
            String requestUrl = API_URL+CHECKOUT_PATH;
            HttpEntity<CheckoutRequest> entity = new HttpEntity(checkoutRequest, setHeaders());
            CheckoutResponse checkoutResponse = restTemplate.postForObject(requestUrl, entity, CheckoutResponse.class);
            return checkoutResponse;
        }catch (RestClientException re){
            logger.error("Error while checkout.", re);
            return null;
        }
    }

    @Override
    public List<Product> getProductsByPrice(int productPrice) {
        try{
            String requestUrl = API_URL+PRODUCT_PATH;
            HttpEntity <String> entity = new HttpEntity<>(setHeaders());
            ProductResponse productResponse = restTemplate.exchange( requestUrl, HttpMethod.GET, entity, ProductResponse.class).getBody();
            return productResponse.getProducts().stream().filter(product -> product.getPrice() > productPrice).collect(Collectors.toList());
        }catch (RestClientException re){
            logger.error("Error while fetching product by price.",re);
            return null;
        }
    }

    public Box findSuitableBox(List<Integer> productIds){
        String requiredDimension = calculateRequiredDimension(productIds);
        String[] dimensionArr = requiredDimension.split(",");
        int width = Integer.valueOf(dimensionArr[0]);
        int height = Integer.valueOf(dimensionArr[1]);
        return findBoxByDimension(width,height).orElse(null);
    }

    private Optional<Box> findBoxByDimension(int width, int height){
        List<Box> boxes = getBoxes().stream()
                .filter(box -> (box.getWidth() >= width && box.getHeight() >= height) || box.getWidth() >= height && box.getHeight() >= width)
                .sorted((a,b) -> (a.getWidth()+b.getHeight()) - (b.getWidth()+b.getHeight()))
                .collect(Collectors.toList()) ;
        return boxes.stream().findFirst();
    }

    private String calculateRequiredDimension(List<Integer> productIdList){
        int requiredWidth=0;
        int requiredHeight=0;
        ListIterator<Integer> listIterator = productIdList.listIterator();
        while(listIterator.hasNext()){
            ProductDimensionResponse pdResponse = getProductDimensions(listIterator.next());
            requiredWidth = requiredWidth+pdResponse.getWidth();
            requiredHeight = requiredHeight+pdResponse.getHeight();
        }
        return requiredWidth+","+requiredHeight;
    }
}

