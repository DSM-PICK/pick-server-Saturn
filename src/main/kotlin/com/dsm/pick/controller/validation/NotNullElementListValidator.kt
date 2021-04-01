package com.dsm.pick.controller.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotNullElementListValidator<T> : ConstraintValidator<NotNullElementList, List<T>> {
    override fun isValid(value: List<T>, context: ConstraintValidatorContext?) =
        value.filter { it == null }.count() <= 0
}