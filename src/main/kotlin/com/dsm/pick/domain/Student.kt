package com.dsm.pick.domain

import javax.persistence.*

@Entity
@Table(name = "student")
class Student(

    @Id
    @Column(name = "num")
    val number: String,

    @Column(name = "name")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "club_name", referencedColumnName = "name")
    val club: Club,

    @ManyToOne
    @JoinColumn(name = "class_name", referencedColumnName = "name")
    val classroom: Classroom,

    @Column(name = "is_monday_self_study")
    val isMondaySelfStudy: Boolean,

    @Column(name = "is_tuesday_self_study")
    val isTuesdaySelfStudy: Boolean,
)