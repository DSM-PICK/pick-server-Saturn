package com.dsm.pick

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@EnableCaching
@SpringBootApplication
class PickApplication

fun main(args: Array<String>) {
    runApplication<PickApplication>(*args)
}
