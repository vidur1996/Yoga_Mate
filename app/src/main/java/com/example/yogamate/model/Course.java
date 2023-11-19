package com.example.yogamate.model;

import java.io.Serializable;

public class Course implements Serializable {
    String className;
    String classTime;
    String classDay;
    int classCapacity;
    double classFees;
    String classType;
    String RoomNo;
    String yogaMat;
    String description;
    int id;

    public Course() {

    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public int getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    public double getClassFees() {
        return classFees;
    }

    public void setClassFees(double classFees) {
        this.classFees = classFees;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public String getYogaMat() {
        return yogaMat;
    }

    public void setYogaMat(String yogaMat) {
        this.yogaMat = yogaMat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
