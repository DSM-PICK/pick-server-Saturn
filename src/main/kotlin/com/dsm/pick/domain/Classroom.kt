package com.dsm.pick.domain

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.converter.FloorConverter
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
    @Convert(converter = FloorConverter::class)
    @NotBlank
    val floor: Floor,

    @Column(name = "priority")
    @Min(0)
    val priority: Int,

    @Column(name = "manager")
    val manager: String?,
) {

    @OneToMany(mappedBy = "classroom")
    val students: List<Student> = listOf()
}