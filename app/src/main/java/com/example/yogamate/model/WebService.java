package com.example.yogamate.model;

import java.util.List;

public class WebService {

    private String userId;
    private List<webCourse> detailList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<webCourse> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<webCourse> detailList) {
        this.detailList = detailList;
    }
}


