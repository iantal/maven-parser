package io.mavenparser.mavenparser.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ParserTest {
    @Test
    fun addProject() {
        val parser = Parser("")
        assertEquals(0, parser.extractedData.size)
        parser.addProject(Project("a"))
        parser.addProject(Project("b"))
        parser.addProject(Project("a"))
        assertEquals(2, parser.extractedData.size)
    }
}