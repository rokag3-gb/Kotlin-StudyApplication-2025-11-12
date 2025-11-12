package com.example.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudyApplication

fun main(args: Array<String>) {
    println("Hello Kotlin & Spring World!")
	runApplication<StudyApplication>(*args)
}