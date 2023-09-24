package vlad.kirilov.runnable

import vlad.kirilov.protocol.receiveSuccessCallback
import vlad.kirilov.protocol.sendFile
import vlad.kirilov.protocol.sendFileName
import vlad.kirilov.protocol.sendFileSize
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.net.Socket

class SendFileRunnable(socket: Socket, private val file: File) : FileRunnable(socket) {
    override fun start() {
        val fileStream = DataInputStream(FileInputStream(file))
        fileStream.use {
            sendFileName(out, file.name)
            sendFileSize(out, file.length())
            sendFile(out, fileStream, buffer)

            val isOk = receiveSuccessCallback(input)
            if (isOk) {
                println("success")
            } else {
                println("fail")
            }
        }
    }
}