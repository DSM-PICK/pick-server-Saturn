package com.dsm.pick.controller

import com.dsm.pick.service.NoticeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class NoticeController(
    private val noticeService: NoticeService,
) {

    @GetMapping("/notice")
    fun findNotice() = noticeService.findNotice()
}