package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Project
import io.mavenparser.mavenparser.core.ProjectType
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class ExtractKtTest {
    @ParameterizedTest
    @MethodSource("projectArguments")
    fun testExtractProject(line: String, expected: Project?) {
        val extractProject = extractProject(line)
        Assertions.assertThat(extractProject).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("projectTypeArguments")
    fun testExtractProjectType(line: String, expected: ProjectType?) {
        val extractedProjectType = extractProjectType(line)
        Assertions.assertThat(extractedProjectType).isEqualTo(expected)
    }

    private companion object {
        @JvmStatic
        fun projectArguments() = Stream.of(
                Arguments.of("[INFO] ----------------< org.apache.zookeeper:zookeeper-jute >-----------------",
                        Project(name = "zookeeper-jute")),
                Arguments.of("[INFO] --------------------------------[ jar ]---------------------------------",
                        null),
                Arguments.of("Downloading ...",
                        null)
        )

        @JvmStatic
        fun projectTypeArguments() = Stream.of(
                Arguments.of("[INFO] ----------------[ pom ]-----------------", ProjectType.POM),
                Arguments.of("[INFO] ----------------[ jar ]-----------------", ProjectType.JAR),
                Arguments.of("[INFO] ----------------[ abc ]-----------------", ProjectType.UNKNOWN),
                Arguments.of("Downloading --------", null)
        )
    }
}