package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.exception.NonExistFloorException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

@javax.persistence.Converter
class FloorConverter : AttributeConverter<Floor, Int>, Converter<String, Floor> {

    override fun convertToDatabaseColumn(floor: Floor) = floor.value

    override fun convertToEntityAttribute(floor: Int) =
        Floor.values().singleOrNull { it.value == floor }?: throw NonExistFloorException(floor)

    override fun convert(request: String) = convertToEntityAttribute(request.toInt())
}