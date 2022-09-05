package com.deverything.candidate.productstore.model;

import java.util.List;

public class CheckoutRequest {
    int boxId;
    List<Integer> productIds;

    public CheckoutRequest(int boxId, List<Integer> productIds) {
        this.boxId = boxId;
        this.productIds = productIds;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
