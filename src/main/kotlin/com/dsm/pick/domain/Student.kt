package com.dsm.pick.domain

import javax.persistence.*

@Entity
@Table(name = "student")
class Student(

    @Id
    @Column(name = "num")
    val number: String,

    @Column(name = "name")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "club_name", referencedColumnName = "name")
    val club: Club,

    @ManyToOne
    @JoinColumn(name = "class_name", referencedColumnName = "name")
    val classroom: Classroom,

    @Column(name = "is_monday_self_study")
    val isMondaySelfStudy: Boolean,

    @Column(name = "is_tuesday_self_study")
    val isTuesdaySelfStudy: Boolean,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (number != other.number) return false
        if (name != other.name) return false
        if (club != other.club) return false
        if (classroom != other.classroom) return false
        if (isMondaySelfStudy != other.isMondaySelfStudy) return false
        if (isTuesdaySelfStudy != other.isTuesdaySelfStudy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + club.hashCode()
        result = 31 * result + classroom.hashCode()
        result = 31 * result + isMondaySelfStudy.hashCode()
        result = 31 * result + isTuesdaySelfStudy.hashCode()
        return result
    }
}