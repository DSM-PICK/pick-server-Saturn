package com.dsm.pick.domain

import javax.persistence.Column
import javax.persistence.Id

class Admin(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "pw")
    val password: String,
)