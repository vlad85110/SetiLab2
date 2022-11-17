package vlad.kirilov.client

import vlad.kirilov.runnable.SendFileRunnable
import java.io.File
import java.net.Socket

class ClientExecutor(private val address: String, private val port: Int, private val filePath: String) {
    fun execute() {
        val socket = Socket(address, port)
        val file = File(filePath)
        SendFileRunnable(socket, file).run()
    }
}