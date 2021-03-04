package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistGradeException(
    grade: Int
) : CommonException(
    code = "NON_EXIST_GRADE",
    message = "허용하지 않는 학년입니다. [grade = ${grade}]",
    status = HttpStatus.BAD_REQUEST,
)