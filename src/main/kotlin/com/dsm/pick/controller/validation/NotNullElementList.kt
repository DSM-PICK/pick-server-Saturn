package com.dsm.pick.controller.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotNullElementListValidator::class])
annotation class NotNullElementList(
    val message: String = "리스트 안에 null 값이 존재합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = [],
)