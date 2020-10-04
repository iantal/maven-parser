package io.mavenparser.mavenparser.core

class Parser(var stateManager: StateManager) {
    fun parse(data: String) : MutableSet<Project?> {
        val lines: List<String> = data.split("\n");
        lines.forEach { line -> stateManager.updateData(line) }
        return stateManager.extractedData
    }
}