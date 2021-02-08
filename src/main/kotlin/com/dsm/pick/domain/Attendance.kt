package com.dsm.pick.domain

import com.dsm.pick.domain.converter.PeriodConverter
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
import com.dsm.pick.domain.converter.StateConverter
import javax.persistence.*

@Entity
@Table(name = "attendance")
class Attendance(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int?,

    @ManyToOne
    @JoinColumn(name = "date", referencedColumnName = "date")
    val activity: Activity,

    @ManyToOne
    @JoinColumn(name = "student_num", referencedColumnName = "num")
    val student: Student,

    @Column(name = "period")
    @Convert(converter = PeriodConverter::class)
    val period: Period,

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    val teacher: Teacher,

    @Column(name = "state")
    @Convert(converter = StateConverter::class)
    var state: State,

    @Column(name = "memo")
    var memo: String,
) {
    
    override fun toString(): String {
        return "Attendance(id=$id, activity=$activity, student=$student, period=$period, teacher=$teacher, state=$state, memo='$memo')"
    }
}