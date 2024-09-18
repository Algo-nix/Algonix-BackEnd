package com.algonix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlgonixApplication

fun main(args: Array<String>) {
	runApplication<AlgonixApplication>(*args)
}
