package com.dsm.pick.domain

import com.dsm.pick.domain.converter.FloorConverter
import com.dsm.pick.domain.attribute.Floor
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "club_location")
class Location(

    @Id
    @Column(name = "location")
    @NotBlank
    val location: String,

    @Column(name = "floor")
    @Convert(converter = FloorConverter::class)
    val floor: Floor,

    @Column(name = "priority")
    val priority: Int,
) {

    override fun toString(): String {
        return "Location(location='$location', floor=$floor, priority=$priority)"
    }
}