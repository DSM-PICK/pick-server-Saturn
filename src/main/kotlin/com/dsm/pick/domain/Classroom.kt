package com.dsm.pick.domain

import javax.persistence.*
import javax.validation.constraints.Min
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
    @Min(0)
    val priority: Int,

    @Column(name = "manager")
    val manager: String?,
) {

    @OneToMany(mappedBy = "classroom")
    val students: List<Student> = listOf()
}