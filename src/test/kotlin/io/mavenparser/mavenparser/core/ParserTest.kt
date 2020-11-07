package io.mavenparser.mavenparser.core

import org.assertj.core.api.Assertions
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

        Assertions.assertThat(extractedData.size).isEqualTo(14)
        Assertions.assertThat(extractedData.count { it?.type == ProjectType.POM }).isEqualTo(7)
        Assertions.assertThat(extractedData.first { project -> project?.name == "zookeeper-jute" }
                ?.libraries
                ?.count { it.type == LibraryType.TRANSITIVE }).isEqualTo(5)
        Assertions.assertThat(extractedData.first { project -> project?.name == "zookeeper-jute" }
                ?.libraries
                ?.count { it.type == LibraryType.DIRECT }).isEqualTo(2)

        Assertions.assertThat(extractedData.first { project -> project?.name == "zookeeper-prometheus-metrics" }
                ?.libraries
                ?.count()).isEqualTo(41)

    }
}