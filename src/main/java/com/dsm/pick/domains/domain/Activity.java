package com.dsm.pick.domains.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "schedule")
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "second_floor_teacher_id", referencedColumnName = "id")
    private Teacher secondFloorTeacher;

    @ManyToOne
    @JoinColumn(name = "third_floor_teacher_id", referencedColumnName = "id")
    private Teacher thirdFloorTeacher;

    @ManyToOne
    @JoinColumn(name = "forth_floor_teacher_id", referencedColumnName = "id")
    private Teacher forthFloorTeacher;

    public Activity() {}
    public Activity(LocalDate date, String schedule, Teacher secondFloorTeacher, Teacher thirdFloorTeacher, Teacher forthFloorTeacher) {
        this.date = date;
        this.schedule = schedule;
        this.secondFloorTeacher = secondFloorTeacher;
        this.thirdFloorTeacher = thirdFloorTeacher;
        this.forthFloorTeacher = forthFloorTeacher;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Teacher getSecondFloorTeacher() {
        return secondFloorTeacher;
    }

    public void setSecondFloorTeacher(Teacher secondFloorTeacher) {
        this.secondFloorTeacher = secondFloorTeacher;
    }

    public Teacher getThirdFloorTeacher() {
        return thirdFloorTeacher;
    }

    public void setThirdFloorTeacher(Teacher thirdFloorTeacher) {
        this.thirdFloorTeacher = thirdFloorTeacher;
    }

    public Teacher getForthFloorTeacher() {
        return forthFloorTeacher;
    }

    public void setForthFloorTeacher(Teacher forthFloorTeacher) {
        this.forthFloorTeacher = forthFloorTeacher;
    }
}
