package io.mavenparser.mavenparser.core

import io.mavenparser.mavenparser.state.ProjectState
import io.mavenparser.mavenparser.state.State


class StateManager {
    val extractedData = mutableSetOf<Project?>()
    private var currentProject: Project? = null
    private var state: State = ProjectState(this)

    fun changeState(state: State) {
        this.state = state
    }

    fun updateCurrentProject(project: Project?) {
        if (currentProject != null) {
            extractedData.add(currentProject)
        }
        currentProject = project
    }

    fun updateCurrentProjectType(type: ProjectType?) {
        currentProject?.type = type
    }

    fun addLibraryToCurrentProject(library: Library) {
        currentProject?.addLibrary(library)
    }

    fun updateData(line: String) {
        state.updateData(line)
    }
}