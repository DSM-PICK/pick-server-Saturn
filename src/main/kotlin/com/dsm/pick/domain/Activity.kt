package com.dsm.pick.domain

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "activity")
class Activity(

    @Id
    @Column(name = "date")
    @NotNull
    val date: LocalDate,

    @Column(name = "schedule")
    @NotBlank
    val schedule: String,

    @ManyToOne
    @JoinColumn(name = "second_floor_teacher_id", referencedColumnName = "id")
    val secondFloorTeacher: Teacher,

    @ManyToOne
    @JoinColumn(name = "third_floor_teacher_id", referencedColumnName = "id")
    val thirdFloorTeacher: Teacher,

    @ManyToOne
    @JoinColumn(name = "forth_floor_teacher_id", referencedColumnName = "id")
    val forthFloorTeacher: Teacher,
)