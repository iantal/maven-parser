package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Parser
import io.mavenparser.mavenparser.core.Project

class ProjectTypeState(val parser: Parser) : State {
    override fun updateData(line: String) {
        TODO("Not yet implemented")
    }

    override fun getData(): MutableSet<Project?> {
        TODO("Not yet implemented")
    }

}