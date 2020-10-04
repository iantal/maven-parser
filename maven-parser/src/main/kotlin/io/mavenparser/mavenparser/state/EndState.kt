package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.StateManager

class EndState(val stateManager: StateManager) : State {
    override fun updateData(line: String) {
        stateManager.updateCurrentProject(null)
    }
}