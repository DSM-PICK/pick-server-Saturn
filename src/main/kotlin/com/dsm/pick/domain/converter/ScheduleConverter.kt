package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.exception.NonExistScheduleException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

@javax.persistence.Converter
class ScheduleConverter : AttributeConverter<Schedule, String>, Converter<String, Schedule> {

    override fun convertToDatabaseColumn(schedule: Schedule) = schedule.value

    override fun convertToEntityAttribute(schedule: String) =
        Schedule.values().singleOrNull { it.value == schedule }?: throw NonExistScheduleException(schedule)

    override fun convert(request: String) = convertToEntityAttribute(request)
}