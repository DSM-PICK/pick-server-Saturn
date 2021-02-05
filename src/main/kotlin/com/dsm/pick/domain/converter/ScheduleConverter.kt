package com.dsm.pick.domain.converter

import com.dsm.pick.domain.converter.attribute.Schedule
import com.dsm.pick.exception.NonExistScheduleException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

class ScheduleConverter : AttributeConverter<Schedule, String>, Converter<String, Schedule> {

    override fun convertToDatabaseColumn(schedule: Schedule) = schedule.value

    override fun convertToEntityAttribute(schedule: String) =
        when (schedule) {
            Schedule.CLUB.value -> Schedule.CLUB
            Schedule.SELF_STUDY.value -> Schedule.SELF_STUDY
            Schedule.AFTER_SCHOOL.value -> Schedule.AFTER_SCHOOL
            else -> throw NonExistScheduleException(schedule)
        }

    override fun convert(request: String) = convertToEntityAttribute(request)
}