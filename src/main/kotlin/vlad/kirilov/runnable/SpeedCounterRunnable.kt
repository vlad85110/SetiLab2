package vlad.kirilov.runnable

import org.apache.commons.lang3.mutable.MutableLong
import java.util.logging.Logger

class SpeedCounterRunnable(private val delay: Long, private val total: MutableLong, private val from: String) :
    Runnable {
    private val logger = Logger.getLogger("speedCounter")
    private var prevTotal = total.value
    private var speedCountCnt = 0

    override fun run() {
        speedCountCnt += 1
        val current = total.value - prevTotal
        prevTotal = total.value

        val currentSpeed = current.toDouble() / delay.toDouble() / 1024
        val averageSpeed = total.toDouble() / (delay * speedCountCnt).toDouble() / 1024

        logger.info("current speed - $currentSpeed kb/s, average - $averageSpeed kb/s")
    }

    fun showTotal(timeTaken: Long) {
        var seconds = timeTaken / 1000
        if (seconds == 0L) {
            seconds = 1L
        }
        logger.info("from $from ${total.value / seconds / 1024} kb/s")
    }
}