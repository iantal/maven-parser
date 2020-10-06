package io.mavenparser.mavenparser

import io.grpc.Server
import io.grpc.ServerBuilder
import io.mavenparser.mavenparser.grpc.ParseService
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.logging.Logger

@SpringBootApplication
class MavenParserApplication

fun main(args: Array<String>) = runBlocking {
    val log = Logger.getLogger(this::class.java.simpleName)

    val server: Server = ServerBuilder
            .forPort(Constants.port)
            .addService(ParseService())
            .build()
    Runtime.getRuntime().addShutdownHook(
			Thread {
				log.info("Shutting down gRPC server since JVM is shutting down")
				server.shutdown()
			}
	)
    log.info("Server started, listening on ${Constants.port}")

    server.start()
    server.awaitTermination()
}
