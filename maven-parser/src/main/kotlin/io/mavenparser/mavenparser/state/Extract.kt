package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.BuildStatus
import io.mavenparser.mavenparser.core.Library
import io.mavenparser.mavenparser.core.Project
import io.mavenparser.mavenparser.core.ProjectType

fun extractProject(line: String): Project? {
    val projectPattern = "\\[INFO\\]\\s*-*<\\s*.*:([^\\s]*)\\s*>-*".toRegex()
    return projectPattern.matchEntire(line)
            ?.destructured
            ?.let { (name) -> Project(name) }
}

fun extractProjectType(line: String): ProjectType? {
    val projectTypePattern = "\\[INFO\\]\\s*-*\\[\\s*([a-z]*)\\s*\\]-*".toRegex()
    return projectTypePattern.matchEntire(line)
            ?.destructured
            ?.let { projectType ->
                when (projectType.component1()) {
                    "pom" -> ProjectType.POM
                    "jar" -> ProjectType.JAR
                    else -> ProjectType.UNKNOWN
                }
            }
}

fun isTransitive(line: String): Boolean {
    return line.split("\\s\\s[+-\\\\]".toRegex()).size > 1
}

fun extractLibrary(line: String): Library? {
    val libraryPattern = "\\[INFO\\]\\s[ +-\\\\|]+(.*:)jar:(.*):(compile|provided|runtime|test|system|import).*".toRegex()
    return libraryPattern.matchEntire(line)
            ?.destructured
            ?.let { (groupAndArtifact, version, libraryScope) -> Library(groupAndArtifact + version, isTransitive(line), libraryScope) }
}

fun extractBuildStatus(line: String) : BuildStatus? {
    val buildPattern = "\\[INFO\\]\\sBUILD\\s(SUCCESS|FAILURE).*".toRegex()
    return buildPattern.matchEntire(line)
            ?.destructured
            ?.let { build ->
                when (build.component1()) {
                    "SUCCESS" -> BuildStatus.SUCCESS
                    "FAILURE" -> BuildStatus.FAILURE
                    else -> BuildStatus.UNKNOWN
                }
            }
}