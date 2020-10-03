package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Parser
import io.mavenparser.mavenparser.core.Project

class ProjectState(val parser: Parser) : State {

    override fun updateData(line: String) {
        val project = extractProject(line)
        if (project != null) {
            parser.addProject(project)
            parser.currentProject = project
            parser.changeState(ProjectTypeState(parser))
            return
        }


    }

    override fun getData(): MutableSet<Project?> {
        return mutableSetOf()
    }
}