package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Project

class EndState : State {
    override fun updateData(line: String) {
        TODO("Not yet implemented")
    }

    override fun getData(): MutableSet<Project?> {
        TODO("Not yet implemented")
    }
}