package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class ProjectTypeState(private val stateManager: StateManager) : State {
    override fun updateData(line: String) {
        val project = extractProject(line)
        if (project != null) {
            println("ProjectType -> Project")
            stateManager.updateCurrentProject(project)
            stateManager.changeState(ProjectState(stateManager))
        }

        val library = extractLibrary(line)
        if (library != null) {
            println("ProjectType -> Library")
            stateManager.addLibraryToCurrentProject(library)
            stateManager.changeState(LibraryState(stateManager))
        }

        val buildStatus = extractBuildStatus(line)
        if (buildStatus != null) {
            println("ProjectType -> End")
            stateManager.changeState(EndState(stateManager))
        }
    }
}