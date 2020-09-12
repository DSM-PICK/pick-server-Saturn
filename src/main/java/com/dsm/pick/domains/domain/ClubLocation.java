package com.dsm.pick.domains.domain;

import javax.persistence.*;

@Entity
@Table(name = "club_location")
public class ClubLocation {

    @Id
    @Column(name = "location")
    private String location;

    @Column(name = "floor")
    private int floor;

    @Column(name = "priority")
    private int priority;

    @OneToOne(mappedBy = "location")
    private Club club;

    public ClubLocation() {}
    public ClubLocation(String location, int floor, int priority) {
        this.location = location;
        this.floor = floor;
        this.priority = priority;
    }
    public ClubLocation(String location, int floor, int priority, Club club) {
        this.location = location;
        this.floor = floor;
        this.priority = priority;
        this.club = club;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
