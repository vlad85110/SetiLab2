package vlad.kirilov.server

import vlad.kirilov.runnable.ReceiveFileRunnable
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ServerExecutor(port: Int) {
    private val socket: ServerSocket = ServerSocket(port)
    private val threadPool: ExecutorService = Executors.newCachedThreadPool()

    fun execute() {
        val curThread = Thread.currentThread()

        val scanThread = Thread {
            val scanner = Scanner(System.`in`)
            var string: String

            do {
                string = scanner.nextLine()
            } while (string != "q")

            println("exit")

            curThread.interrupt()
            socket.close()
        }
        scanThread.start()

        while (!curThread.isInterrupted) {
            val file = File("uploads")
            if (!file.exists()) {
                file.mkdir()
            }
            val newSocket: Socket

            try {
                newSocket = socket.accept()
                threadPool.execute(ReceiveFileRunnable(newSocket))
            } catch (e: SocketException) {
                if (!curThread.isInterrupted) {
                    println("socket is closed")
                }
            }
        }
    }
}