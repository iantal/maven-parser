package io.mavenparser.mavenparser.core

import org.junit.jupiter.api.Test

internal class ParserTest {
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun readFile(fileName: String): String {
        return this::class.java.classLoader.getResource(fileName).readText()
    }

    @Test
    fun parse() {
        val data = readFile("zookeeper.txt")
        val parser = Parser(StateManager())
        val extractedData = parser.parse(data)

        extractedData.forEach { println(it) }
    }
}