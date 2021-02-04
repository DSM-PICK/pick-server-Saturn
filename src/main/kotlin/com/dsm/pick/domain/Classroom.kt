package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "class")
class Classroom(

    @Id
    @Column(name = "name")
    val name: String,

    @Column(name = "floor")
    val floor: String,

    @Column(name = "priority")
    val priority: String,

    @Column(name = "manager")
    val manager: String?,
)