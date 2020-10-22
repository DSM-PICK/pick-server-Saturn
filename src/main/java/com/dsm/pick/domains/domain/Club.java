package com.dsm.pick.domains.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "location", referencedColumnName = "location")
    private ClubLocation location;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "club_head")
    private String head;

    @OneToMany(mappedBy = "club")
    private List<Student> students;

    public Club() {}
    public Club(String name, ClubLocation location, String teacher, String head) {
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClubLocation getLocation() {
        return location;
    }

    public void setLocation(ClubLocation location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
