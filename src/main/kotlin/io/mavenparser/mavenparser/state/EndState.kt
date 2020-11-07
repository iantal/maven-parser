package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class EndState(private val stateManager: StateManager) : State {
    override fun updateData(line: String) {
        stateManager.updateCurrentProject(null)
    }
}