package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.State
import com.dsm.pick.exception.NonExistStateException
import org.springframework.core.convert.converter.Converter
import javax.persistence.AttributeConverter

@javax.persistence.Converter
class StateConverter : AttributeConverter<State, String>, Converter<String, State> {

    override fun convertToDatabaseColumn(state: State?) = state?.value

    override fun convertToEntityAttribute(state: String?) =
        State.values().singleOrNull { it.value == state }?: throw NonExistStateException(state?: "-1")

    override fun convert(request: String) = convertToEntityAttribute(request)
}