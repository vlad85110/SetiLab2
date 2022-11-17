package vlad.kirilov.runnable

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

abstract class FileRunnable(protected val socket: Socket): Runnable {
    protected val out: DataOutputStream = DataOutputStream(socket.getOutputStream())
    protected val input: DataInputStream = DataInputStream(socket.getInputStream())
    protected val buffer = ByteArray(10000)

    override fun run() {
        input.use {
            out.use {
                start()
            }
        }
    }

    abstract fun start()
}