package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Project
import io.mavenparser.mavenparser.core.ProjectType

fun extractProject(line: String): Project? {
    val projectPattern = "\\[INFO\\]\\s*-*<\\s*.*:([^\\s]*)\\s*>-*".toRegex()
    return projectPattern.matchEntire(line)
            ?.destructured
            ?.let { (name) -> Project(name) }
}

fun extractProjectType(line: String): ProjectType? {
    val projectPattern = "\\[INFO\\]\\s*-*\\[\\s*([a-z]*)\\s*\\]-*".toRegex()
    return projectPattern.matchEntire(line)
            ?.destructured
            ?.let { projectType ->
                when (projectType.component1()) {
                    "pom" -> ProjectType.POM
                    "jar" -> ProjectType.JAR
                    else -> ProjectType.UNKNOWN
                }
            }
}