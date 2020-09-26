package io.mavenparseer.mavenparser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MavenParserApplication

fun main(args: Array<String>) {
	runApplication<MavenParserApplication>(*args)
}
