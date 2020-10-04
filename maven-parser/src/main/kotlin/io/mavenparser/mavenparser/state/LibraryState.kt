package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class LibraryState(private val stateManager: StateManager) : State {
    override fun updateData(line: String) {
        val project = extractProject(line)
        if (project != null) {
            println("Library -> Project")
            stateManager.updateCurrentProject(project)
            stateManager.changeState(ProjectState(stateManager))
            return
        }

        val library = extractLibrary(line)
        if (library != null) {
            println("Library -> Library")
            stateManager.addLibraryToCurrentProject(library)
            return
        }

        val buildStatus = extractBuildStatus(line)
        if (buildStatus != null) {
            println("Project -> End")
            stateManager.changeState(EndState(stateManager))
            return
        }
    }
}