package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.BuildStatus
import io.mavenparser.mavenparser.core.Library
import io.mavenparser.mavenparser.core.LibraryType
import io.mavenparser.mavenparser.core.Project
import io.mavenparser.mavenparser.core.ProjectType
import io.mavenparser.mavenparser.core.Scope
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
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

    @ParameterizedTest
    @MethodSource("transitivityCases")
    fun testIsTransitive(line: String, expectedResult: Boolean) {
        val actualResult = isTransitive(line)
        Assertions.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("libraryNegativeCases")
    fun testExtractLibrary(line: String, expectedResult: Library?) {
        val actualResult = extractLibrary(line)
        Assertions.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("libraryPositiveCases")
    fun testExtractLibrary(line: String, name: String, type: LibraryType, scope: Scope) {
        val actualResult = extractLibrary(line)
        println(actualResult)
        Assertions.assertThat(actualResult?.name).isEqualTo(name)
        Assertions.assertThat(actualResult?.type).isEqualTo(type)
        Assertions.assertThat(actualResult?.scope).isEqualTo(scope)
    }

    @Test
    fun extractBuildState() {
        val success = extractBuildStatus("[INFO] BUILD SUCCESS")
        val failure = extractBuildStatus("[INFO] BUILD FAILURE")
        val unknown = extractBuildStatus("[INFO] BUILD ...")

        Assertions.assertThat(success).isEqualTo(BuildStatus.SUCCESS)
        Assertions.assertThat(failure).isEqualTo(BuildStatus.FAILURE)
        Assertions.assertThat(unknown).isEqualTo(null)
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

        @JvmStatic
        fun transitivityCases() = Stream.of(
                Arguments.of("[INFO]    \\- com.google.code.findbugs:jsr305:jar:3.0.2:provided (optional)", true),
                Arguments.of("[INFO] |  +- org.slf4j:slf4j-log4j12:jar:1.7.25:compile", true),
                Arguments.of("[INFO] |  |  +- io.netty:netty-resolver:jar:4.1.50.Final:compile\n", true),
                Arguments.of("[INFO] |  |  \\- io.netty:netty-transport-native-unix-common:jar:4.1.50.Final:compile", true),
                Arguments.of("[INFO] +- org.apache.zookeeper:zookeeper:jar:3.7.0-SNAPSHOT:compile\n", false)
        )

        @JvmStatic
        fun libraryNegativeCases() = Stream.of(
                Arguments.of("[INFO] --- maven-dependency-plugin:3.1.1:tree (default-cli) @ zookeeper ---", null),
                Arguments.of("[INFO] org.apache.zookeeper:zookeeper:jar:3.7.0-SNAPSHOT", null),
                Arguments.of("Downloading from apache.snapshots: --- ", null)
        )

        @JvmStatic
        fun libraryPositiveCases() = Stream.of(
                Arguments.of("[INFO] +- com.github.spotbugs:spotbugs-annotations:jar:4.0.2:provided (optional)", "com.github.spotbugs:spotbugs-annotations:4.0.2", LibraryType.DIRECT, Scope.PROVIDED),
                Arguments.of("[INFO] |  +- org.apache.yetus:audience-annotations:jar:0.5.0:compile", "org.apache.yetus:audience-annotations:0.5.0", LibraryType.TRANSITIVE, Scope.COMPILE),
                Arguments.of("[INFO] |  |  \\- io.netty:netty-codec:jar:4.1.50.Final:compile", "io.netty:netty-codec:4.1.50.Final", LibraryType.TRANSITIVE, Scope.COMPILE),
                Arguments.of("[INFO] |  |  +- io.netty:netty-transport:jar:4.1.50.Final:compile", "io.netty:netty-transport:4.1.50.Final", LibraryType.TRANSITIVE, Scope.COMPILE),
                Arguments.of("[INFO] \\- org.junit.platform:junit-platform-runner:jar:1.6.2:test", "org.junit.platform:junit-platform-runner:1.6.2", LibraryType.DIRECT, Scope.TEST)
        )
    }
}