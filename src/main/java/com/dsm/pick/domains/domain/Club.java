package com.dsm.pick.domains.domain;

import javax.persistence.*;

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

    public Club() {}
    public Club(String name, ClubLocation location, String teacher) {
        this.name = name;
        this.location = location;
        this.teacher = teacher;
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
}
