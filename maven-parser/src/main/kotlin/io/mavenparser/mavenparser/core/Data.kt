package io.mavenparser.mavenparser.core

enum class LibraryType { DIRECT, TRANSITIVE }

enum class ProjectType { POM, JAR, UNKNOWN }

data class Project(var name: String) {
    private val libraries = mutableListOf<Library>()

    fun addLibrary(library: Library) {
        libraries.add(library)
    }
}

data class Library(var name: String, val libraryType: LibraryType)
