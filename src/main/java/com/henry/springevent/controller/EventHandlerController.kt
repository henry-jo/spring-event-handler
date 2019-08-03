package com.henry.springevent.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event/handler")
class EventHandlerController {

    @GetMapping("/test")
    fun test(): String {
        return "HELLO"
    }
}