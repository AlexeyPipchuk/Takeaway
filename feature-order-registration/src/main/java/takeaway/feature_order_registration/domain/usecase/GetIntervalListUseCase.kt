package takeaway.feature_order_registration.domain.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetIntervalListUseCase @Inject constructor() {

    private companion object {
        const val TIME_PATTERN = "HH:mm"
        const val interval = 900000L // 15 мин
    }

    operator fun invoke(firstTime: String, secondTime: String): List<String> {
        fun getHours(time: String): Int =
            time.substringBefore(":").toInt()

        fun getMinutes(time: String): Int =
            time.substringAfter(":").toInt()

        val fromDateCalendar = Calendar.getInstance()
        fromDateCalendar.set(Calendar.HOUR_OF_DAY, getHours(firstTime))
        fromDateCalendar.set(Calendar.MINUTE, getMinutes(firstTime))
        fromDateCalendar.set(Calendar.SECOND, 0)
        val fromMillis = fromDateCalendar.time.time

        val workToCalendar = Calendar.getInstance()
        workToCalendar.set(Calendar.HOUR_OF_DAY, getHours(secondTime))
        workToCalendar.set(Calendar.MINUTE, getMinutes(secondTime))
        workToCalendar.set(Calendar.SECOND, 0)
        val toMillis = workToCalendar.time.time

        val timeFormatter = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        val currentDate = Date()

        var start = currentDate.time - fromMillis
        start = if (start >= 0) currentDate.time
        else fromMillis

        val workTimeTodayMillis = toMillis - start
        return if (workTimeTodayMillis <= 0 || workTimeTodayMillis < interval) emptyList()
        else {
            val timeList = mutableListOf<String>()
            val intervalCounts = workTimeTodayMillis / interval - 1
            for (i in 1..intervalCounts) {
                val item = toMillis - interval * i
                timeList.add(timeFormatter.format(Date(item)))
            }
            timeList
        }
    }
}