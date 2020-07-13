package takeaway.shared_error

import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorConverter {

    fun convert(error: Throwable): ErrorType =
        if (error is UnknownHostException || error is SocketTimeoutException) {
            ErrorType.BAD_INTERNET
        } else {
            ErrorType.SERVICE_UNAVAILABLE
        }
}

enum class ErrorType {
    BAD_INTERNET,
    SERVICE_UNAVAILABLE
}