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

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_name", referencedColumnName = "name")
    val club: Club,

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_name", referencedColumnName = "name")
    val classroom: Classroom,

    @Column(name = "is_self_study")
    val isSelfStudy: Boolean,

    @Column(name = "is_replenishment")
    val isReplenishment: Boolean,
)