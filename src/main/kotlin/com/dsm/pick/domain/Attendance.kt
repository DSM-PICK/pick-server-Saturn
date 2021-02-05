package com.dsm.pick.domain

import javax.persistence.*

class Attendance(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int?,

    @ManyToOne
    @JoinColumn(name = "date", referencedColumnName = "date")
    val activity: Activity,

    @ManyToOne
    @JoinColumn(name = "student_num", referencedColumnName = "num")
    val student: Student,

    @Column(name = "period")
    val period: Int,

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    val teacher: Teacher,

    @Column(name = "state")
    val state: String,

    @Column(name = "memo")
    val memo: String,
)