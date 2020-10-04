package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class ProjectState(private val stateManager: StateManager) : State {

    override fun updateData(line: String) {
        val project = extractProject(line)
        if (project != null) {
            println("Project -> Project")
            stateManager.updateCurrentProject(project)
            return
        }

        val projectType = extractProjectType(line)
        if (projectType != null) {
            println("Project -> ProjectType")
            stateManager.updateCurrentProjectType(projectType)
            stateManager.changeState(ProjectTypeState(stateManager))
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