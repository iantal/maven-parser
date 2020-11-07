package io.mavenparser.mavenparser.core

import java.time.Instant
import java.util.logging.Logger

class Parser(var stateManager: StateManager) {
    val log: Logger = Logger.getLogger(this::class.java.simpleName)

    fun parse(data: String) : MutableSet<Project?> {
        val lines: List<String> = data.split("\n");
        lines.forEach { line -> stateManager.updateData(line) }
        val elapsedTime = Instant.now().toEpochMilli() - stateManager.start
        log.info("Finished parsing in $elapsedTime ms")
        return stateManager.extractedData
    }
}