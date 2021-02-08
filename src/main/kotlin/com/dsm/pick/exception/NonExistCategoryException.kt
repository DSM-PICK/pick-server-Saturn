package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistCategoryException(
    category: String
) : CommonException(
    code = "NON_EXIST_FLOOR",
    message = "허용하지 않는 공지 카테고리입니다. [category = ${category}]",
    status = HttpStatus.BAD_REQUEST,
)