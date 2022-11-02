package vlad.kirilov.protocol

import org.apache.commons.lang3.mutable.MutableLong
import java.io.DataInputStream
import java.io.DataOutputStream

fun sendFile(outputStream: DataOutputStream, fileStream: DataInputStream, buffer: ByteArray) {
    var cnt = fileStream.read(buffer)

    while (cnt > 0) {
        sendPacketSize(outputStream, cnt)
        outputStream.write(buffer, 0, cnt)
        outputStream.flush()
        cnt = fileStream.read(buffer)
    }
    sendPacketSize(outputStream,0)
}

fun receiveFile(inputStream: DataInputStream, fileStream: DataOutputStream, buffer: ByteArray, total: MutableLong) {
    var readCnt: Int
    var receiveSize = receivePacketSize(inputStream)
    var sendLeft = receiveSize

    while (receiveSize > 0) {
        while (sendLeft > 0) {
            val readSize = if (receiveSize <= buffer.size) {receiveSize} else {buffer.size}
            readCnt = inputStream.read(buffer, 0, readSize)
            sendLeft -= readCnt
            total.add(readCnt.toLong())
            fileStream.write(buffer, 0, readCnt)
        }

        receiveSize = receivePacketSize(inputStream)
        sendLeft = receiveSize
    }
}

fun sendFileName(outputStream: DataOutputStream, name: String) {
    outputStream.writeUTF(name)
}

fun receiveFileName(inputStream: DataInputStream): String {
    return inputStream.readUTF()
}

fun sendFileSize(outputStream: DataOutputStream, fileSize: Long) {
    outputStream.writeLong(fileSize)
}

fun receiveFileSize(inputStream: DataInputStream): Long {
    return inputStream.readLong()
}

fun sendPacketSize(outputStream: DataOutputStream, packetSize: Int) {
    outputStream.writeInt(packetSize)
}

fun receivePacketSize(inputStream: DataInputStream): Int {
    return inputStream.readInt()
}

fun sendSuccessCallback(outputStream: DataOutputStream, isSuccess: Boolean) {
    outputStream.writeBoolean(isSuccess)
}

fun receiveSuccessCallback(inputStream: DataInputStream): Boolean {
    return inputStream.readBoolean()
}