package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "teacher")
class Teacher(

    @Id
    @Column(name = "id")
    @NotBlank
    val id: String,

    @Column(name = "pw")
    @NotBlank
    var password: String,

    @Column(name = "name")
    @NotBlank
    val name: String,

    @Column(name = "office")
    @NotBlank
    val office: String,
) {

    override fun toString(): String {
        return "Teacher(id='$id', password='$password', name='$name', office='$office')"
    }
}