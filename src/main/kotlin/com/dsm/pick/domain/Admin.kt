package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "admin")
class Admin(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "pw")
    val password: String,
)