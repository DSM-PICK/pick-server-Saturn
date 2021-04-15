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

    @OneToOne(optional = false)
    @JoinColumn(name = "location", referencedColumnName = "location")
    val location: Location,

    @Column(name = "teacher")
    val teacher: String?,

    @Column(name = "club_head")
    val head: String?,
) {

    @OneToMany(mappedBy = "club")
    var students: List<Student> = listOf()
}