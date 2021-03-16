package com.dsm.pick.service.external

import com.dsm.pick.domain.attribute.State
import com.dsm.pick.exception.InvalidApiKeyException
import com.dsm.pick.exception.NonExistStateException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ExternalValidationService(
    @Value("\${API_KEY:apiKey}")
    private val apiKey: String,
) {

    fun validateApiKey(apiKey: String) =
        if (this.apiKey == apiKey)
            true
        else
            throw InvalidApiKeyException()

    fun validateStudentState(studentState: String) =
        if (studentState == State.MOVE.value || studentState == State.ATTENDANCE.value)
            true
        else
            throw NonExistStateException(studentState)
}