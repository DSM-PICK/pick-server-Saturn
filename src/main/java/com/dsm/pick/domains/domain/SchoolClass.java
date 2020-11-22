package com.dsm.pick.domains.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "class")
public class SchoolClass {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "floor")
    private int floor;

    @Column(name = "priority")
    private int priority;

    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students;

    public SchoolClass() {}
    public SchoolClass(String name, int floor, int priority, List<Student> students) {
        this.name = name;
        this.floor = floor;
        this.priority = priority;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
