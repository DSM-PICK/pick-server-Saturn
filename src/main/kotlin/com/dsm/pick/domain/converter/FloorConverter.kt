package com.dsm.pick.domain.converter

import com.dsm.pick.domain.converter.attribute.Floor
import com.dsm.pick.exception.NonExistFloorException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

class FloorConverter : AttributeConverter<Floor, Int>, Converter<String, Floor> {

    override fun convertToDatabaseColumn(floor: Floor) = floor.value.toInt()

    override fun convertToEntityAttribute(floor: Int) =
        when (floor) {
            1 -> Floor.ONE
            2 -> Floor.TWO
            3 -> Floor.THREE
            4 -> Floor.FOUR
            else -> throw NonExistFloorException(floor)
        }

    override fun convert(request: String) = convertToEntityAttribute(request.toInt())
}