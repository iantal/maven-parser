package io.mavenparser.mavenparser.grpc

import io.mavenparser.mavenparser.Library
import io.mavenparser.mavenparser.MavenParseServiceGrpcKt
import io.mavenparser.mavenparser.ParseRequest
import io.mavenparser.mavenparser.ParseResponse
import io.mavenparser.mavenparser.Project
import io.mavenparser.mavenparser.core.Parser
import io.mavenparser.mavenparser.core.StateManager
import java.util.*
import java.util.logging.Logger


class ParseService : MavenParseServiceGrpcKt.MavenParseServiceCoroutineImplBase() {
    val log: Logger = Logger.getLogger(this::class.java.simpleName)

    override suspend fun parse(request: ParseRequest): ParseResponse {
        log.info("Received maven parse request")

        val decodedData = decodeBase64(request.data)
        val parser = Parser(StateManager())
        val parsedData = parser.parse(decodedData)

        val projects = mutableListOf<Project>()
        for (project in parsedData) {
            val libs = mutableListOf<Library>()
            for (library in project?.libraries!!) {
                libs.add(Library.newBuilder()
                        .setName(library.name)
                        .setType(library.type.toString().toLowerCase())
                        .setScope(library.scope.toString().toLowerCase())
                        .build())
            }
            projects.add(Project.newBuilder()
                    .setName(project.name)
                    .addAllLibraries(libs)
                    .build())
        }

        return ParseResponse.newBuilder()
                .addAllProjects(projects)
                .build()
    }

    private fun decodeBase64(data: String) : String{
        val decoder = Base64.getDecoder()
        val decoded = decoder.decode(data)
        return String(decoded, Charsets.UTF_8)
    }
}