package com.dsm.pick.domains.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class SchoolClass {

    @Id
    private String name;

    private int floor;

    private int priority;

    public SchoolClass() {}
    public SchoolClass(String name, int floor, int priority) {
        this.name = name;
        this.floor = floor;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
