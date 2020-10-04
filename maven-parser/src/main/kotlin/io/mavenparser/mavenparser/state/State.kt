package io.mavenparser.mavenparser.state

interface State {
    fun updateData(line: String)
}