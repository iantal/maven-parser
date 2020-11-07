package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class ProjectTypeState(private val stateManager: StateManager) : State {
    override fun updateData(line: String) {
        val project = extractProject(line)
        if (project != null) {
            stateManager.updateCurrentProject(project)
            stateManager.changeState(ProjectState(stateManager))
            return
        }

        val library = extractLibrary(line)
        if (library != null) {
            stateManager.addLibraryToCurrentProject(library)
            stateManager.changeState(LibraryState(stateManager))
            return
        }

        val buildStatus = extractBuildStatus(line)
        if (buildStatus != null) {
            stateManager.changeState(EndState(stateManager))
            return
        }
    }
}