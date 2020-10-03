package io.mavenparser.mavenparser.core

enum class LibraryType {
    DIRECT,
    TRANSITIVE
}

enum class ProjectType {
    POM,
    JAR,
    UNKNOWN
}

enum class Scope {
    COMPILE,
    PROVIDED,
    RUNTIME,
    TEST,
    SYSTEM,
    IMPORT,
    UNKNOWN
}

data class Project(var name: String) {
    private val libraries = mutableListOf<Library>()

    fun addLibrary(library: Library) {
        libraries.add(library)
    }
}

data class Library(
        var name: String,
        private val isTransitive: Boolean,
        private val libraryScope: String
) {
    val type = if (isTransitive) LibraryType.TRANSITIVE else LibraryType.DIRECT
    val scope = when(libraryScope) {
        "compile" -> Scope.COMPILE
        "provided" -> Scope.PROVIDED
        "runtime" -> Scope.RUNTIME
        "test" -> Scope.TEST
        "system" -> Scope.SYSTEM
        "import" -> Scope.IMPORT
        else -> Scope.UNKNOWN
    }

}
