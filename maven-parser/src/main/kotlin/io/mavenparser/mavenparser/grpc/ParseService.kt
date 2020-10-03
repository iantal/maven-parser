package io.mavenparser.mavenparser.grpc

import io.mavenparser.mavenparser.MavenParseServiceGrpcKt
import io.mavenparser.mavenparser.ParseRequest
import io.mavenparser.mavenparser.ParseResponse
import io.mavenparser.mavenparser.Project

class ParseService : MavenParseServiceGrpcKt.MavenParseServiceCoroutineImplBase() {
    override suspend fun parse(request: ParseRequest): ParseResponse {
        println("received maven parse request for ${request.data}")

        val libraries = listOf("abs", "ade")

        val projects = listOf(Project.newBuilder()
                .setName("library1")
                .addAllLibraries(libraries)
                .build()
        )

        return ParseResponse.newBuilder()
                .addAllProjects(projects)
                .build()
    }
}