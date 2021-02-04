package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "class")
class Classroom(

    @Id
    @Column(name = "name")
    @NotBlank
    val name: String,

    @Column(name = "floor")
    @NotBlank
    val floor: String,

    @Column(name = "priority")
    @NotBlank
    val priority: String,

    @Column(name = "manager")
    val manager: String?,
)