package com.dsm.pick.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "club")
class Club(

    @Id
    @Column(name = "name")
    @NotBlank
    val name: String,

    @OneToOne
    @JoinColumn(name = "location", referencedColumnName = "location")
    val location: Location,

    @Column(name = "teacher")
    val teacher: String?,

    @Column(name = "club_head")
    val head: String?,
) {

    @OneToMany(mappedBy = "club")
    val students: List<Student> = listOf()

    override fun toString(): String {
        return "Club(name='$name', location=$location, teacher=$teacher, head=$head, students=$students)"
    }
}