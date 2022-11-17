package vlad.kirilov

import vlad.kirilov.client.ClientExecutor
import vlad.kirilov.server.ServerExecutor
import java.io.FileNotFoundException
import java.net.ConnectException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("wrong args: first argument must be \"client\" or \"server\"")
        return
    }

    when (args.first()) {
        "client" -> {
            val fileName: String
            val address: String
            val port: Int

            if (args.size != 4) {
                System.err.println("wrong args: expected 4, have ${args.size}")
                System.err.println("args: client <address> <port> <fileName>")
                return
            }

            try {
                address = args[1]
                port = Integer.parseInt(args[2])
                fileName = args[3]
            } catch (e: NumberFormatException) {
                System.err.println("port must be integer number")
                System.err.println("args: client <address> <port> <fileName>")
                return
            }

            try {
                ClientExecutor(address, port, fileName).execute()
            } catch (e: FileNotFoundException) {
                System.err.println("incorrect file path")
            } catch (e: ConnectException) {
                System.err.println("$address:$port: ${e.message}")
            }
        }
        "server" -> {
            val port: Int

            if (args.size != 2) {
                System.err.println("wrong args: expected 2, have ${args.size}")
                System.err.println("args: server <port>")
                return
            }

            try {
                port = Integer.parseInt(args[1])
            } catch (e: NumberFormatException) {
                System.err.println("port must be integer number")
                return
            }

            ServerExecutor(port).execute()
        }
        else -> {
            System.err.println("wrong args: first argument must be \"client\" or \"server\"")
        }
    }
}