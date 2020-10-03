package io.mavenparser.mavenparser.core

import io.mavenparser.mavenparser.state.ProjectState
import io.mavenparser.mavenparser.state.State


class Parser(val data: String) {
    val extractedData = mutableSetOf<Project?>()
    var currentProject : Project? = null
    var state: State = ProjectState(this)

    fun addProject(project: Project?) {
        extractedData.add(project)
    }

    fun changeState(state: State) {
        this.state = state
    }

}