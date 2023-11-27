package com.example.yogamate.model;

import java.io.Serializable;
import java.util.List;

public class SaveCourse implements Serializable {
    private int classCapacity;
    private String classDay;
    private int classFees;
    private String className;
    private String classTime;
    private String classType;
    private String description;
    private int id;
    private String roomNo;
    private String yogaMat;
    private List<Instance> instances;

    public int getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public int getClassFees() {
        return classFees;
    }

    public void setClassFees(int classFees) {
        this.classFees = classFees;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getYogaMat() {
        return yogaMat;
    }

    public void setYogaMat(String yogaMat) {
        this.yogaMat = yogaMat;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
}
