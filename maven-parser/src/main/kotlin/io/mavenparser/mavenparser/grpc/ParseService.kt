package io.mavenparser.mavenparser.grpc

import com.google.common.io.BaseEncoding.base64
import io.mavenparser.mavenparser.Library
import io.mavenparser.mavenparser.MavenParseServiceGrpcKt
import io.mavenparser.mavenparser.ParseRequest
import io.mavenparser.mavenparser.ParseResponse
import io.mavenparser.mavenparser.Project
import io.mavenparser.mavenparser.core.Parser
import io.mavenparser.mavenparser.core.StateManager
import java.lang.Byte.decode
import java.nio.charset.StandardCharsets
import java.util.*


class ParseService : MavenParseServiceGrpcKt.MavenParseServiceCoroutineImplBase() {
    override suspend fun parse(request: ParseRequest): ParseResponse {
        println("received maven parse request for ${request.data}")

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

    fun decodeBase64(data: String) : String{
        val decoder = Base64.getDecoder()
        val decoded = decoder.decode(data)
        val decodedStr = String(decoded, Charsets.UTF_8)
        println(decodedStr)
        return decodedStr
    }
}