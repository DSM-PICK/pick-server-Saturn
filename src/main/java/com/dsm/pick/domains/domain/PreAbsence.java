package com.dsm.pick.domains.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pre_absence")
public class PreAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "student_num", referencedColumnName = "num")
    private Student student;

    @Column(name = "start_period")
    private int startPeriod;

    @Column(name = "end_period")
    private int endPeriod;

    @Column(name = "state")
    private String state;

    public PreAbsence() {}
    public PreAbsence(int id, Teacher teacher, LocalDate startDate, LocalDate endDate, Student student, int startPeriod, int endPeriod, String state) {
        this.id = id;
        this.teacher = teacher;
        this.startDate = startDate;
        this.endDate = endDate;
        this.student = student;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(int startPeriod) {
        this.startPeriod = startPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(int endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
