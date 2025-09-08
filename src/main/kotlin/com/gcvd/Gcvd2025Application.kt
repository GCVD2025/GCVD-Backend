package com.gcvd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class Gcvd2025Application

fun main(args: Array<String>) {
    runApplication<Gcvd2025Application>(*args)
}
