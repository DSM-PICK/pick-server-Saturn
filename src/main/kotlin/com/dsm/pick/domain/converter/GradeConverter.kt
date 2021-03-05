package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.Grade
import com.dsm.pick.exception.NonExistGradeException
import org.springframework.core.convert.converter.Converter

class GradeConverter : Converter<String, Grade> {

    override fun convert(request: String) =
        Grade.values().singleOrNull { it.value == request.toInt() }?: throw NonExistGradeException(request.toInt())
}