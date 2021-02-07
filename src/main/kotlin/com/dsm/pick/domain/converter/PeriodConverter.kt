package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.exception.NonExistPeriodException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

class PeriodConverter : AttributeConverter<Period, Int>, Converter<String, Period> {

    override fun convertToDatabaseColumn(period: Period) = period.value

    override fun convertToEntityAttribute(period: Int) =
        Period.values().singleOrNull { it.value == period }?: throw NonExistPeriodException(period)

    override fun convert(request: String) = convertToEntityAttribute(request.toInt())
}