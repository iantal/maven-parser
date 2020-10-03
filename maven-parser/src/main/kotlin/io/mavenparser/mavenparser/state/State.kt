package io.mavenparser.mavenparser.state

import io.mavenparser.mavenparser.core.Project

interface State {
    fun updateData(line: String)
    fun getData(): MutableSet<Project?>
}