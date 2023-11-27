package com.example.yogamate.model;

import java.util.List;

public class WebService {

    private String userId;
    private List<SaveCourse> detailList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SaveCourse> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SaveCourse> detailList) {
        this.detailList = detailList;
    }
}


