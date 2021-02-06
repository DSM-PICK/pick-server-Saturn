package com.dsm.pick.domain.converter

import com.dsm.pick.domain.converter.attribute.Period
import com.dsm.pick.exception.NonExistPeriodException
import javax.persistence.AttributeConverter

class PeriodConverter : AttributeConverter<Period, Int> {

    override fun convertToDatabaseColumn(period: Period) = period.value

    override fun convertToEntityAttribute(period: Int) =
        Period.values().singleOrNull { it.value == period }?: throw NonExistPeriodException(period)
}