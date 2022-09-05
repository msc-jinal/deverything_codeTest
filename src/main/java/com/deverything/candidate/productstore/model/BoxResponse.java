package com.deverything.candidate.productstore.model;

import java.util.List;

public class BoxResponse {
    int statusCode;
    List<Box> boxes;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
