package vlad.kirilov

import vlad.kirilov.client.ClientExecutor
import vlad.kirilov.server.ServerExecutor
import java.io.FileNotFoundException
import java.net.ConnectException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("wrong args")
        return
    }

    when (args.first()) {
        "client" -> {
            val fileName: String
            val address: String
            val port: Int

            try {
                address = args[1]
                port = Integer.parseInt(args[2])
                fileName = args[3]
            } catch (e: Exception) {
                println("wrong args")
                return
            }

            try {
                ClientExecutor(address, port, fileName).execute()
            } catch (e: FileNotFoundException) {
                println("incorrect file path")
            } catch (e: ConnectException) {
                println("incorrect host")
            }
        }
        "server" -> {
            val port: Int

            try {
                port = Integer.parseInt(args[1])
            } catch (e: Exception) {
                println("wrong args")
                return
            }

            ServerExecutor(port).execute()
        }
        else -> {
            println("wrong args")
        }
    }
}