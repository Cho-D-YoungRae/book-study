package com.example.ch01springaiproject.controller

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AiController {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(
        value = ["/ai/chat"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun chat(@RequestParam question: String): String {
        return "아직 모델과 연결되지 않았습니다"
    }
}