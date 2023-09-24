package vlad.kirilov.runnable

import org.apache.commons.lang3.mutable.MutableLong
import vlad.kirilov.protocol.receiveFile
import vlad.kirilov.protocol.receiveFileName
import vlad.kirilov.protocol.receiveFileSize
import vlad.kirilov.protocol.sendSuccessCallback
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ReceiveFileRunnable(socket: Socket): FileRunnable(socket) {
    override fun start() {
        val delay: Long = 3
        val total = MutableLong(0)
        val speedCounterRunnable = SpeedCounterRunnable(delay, total, socket.remoteSocketAddress.toString())
        val speedCounter = Executors.newSingleThreadScheduledExecutor()

        val fileName = receiveFileName(input)
        val expectedSize = receiveFileSize(input)
        val file = File("uploads/${fileName}")
        val fileStream = DataOutputStream(FileOutputStream(file))

        fileStream.use {
            val startTime = System.currentTimeMillis()
            speedCounter.scheduleAtFixedRate(speedCounterRunnable, delay, delay, TimeUnit.SECONDS)
            receiveFile(input, fileStream, buffer, total)
            val endTime = System.currentTimeMillis()
            speedCounter.shutdown()
            speedCounterRunnable.showTotal(endTime - startTime)
            sendSuccessCallback(out, expectedSize == total.value)
        }
    }
}
