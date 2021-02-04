package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "teacher")
class Teacher(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "pw")
    var password: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "office")
    val office: String,
)