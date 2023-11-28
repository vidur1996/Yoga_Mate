package com.example.yogamate.model;

import java.util.List;

public class webCourse {
    String dayOfWeek,timeOfDay;
    private List<webInstance>  classList;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public List<webInstance> getClassList() {
        return classList;
    }

    public void setClassList(List<webInstance> classList) {
        this.classList = classList;
    }
}
