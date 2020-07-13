package takeaway.app

import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

fun Fragment.showServiceUnavailableDialog(positiveResult: () -> Unit, negativeResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(R.string.service_unavailable_dialog_title)
        .setMessage(R.string.service_unavailable_dialog_message)
        .setPositiveButton(R.string.repeat_dialog_button_text) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(R.string.cancel_dialog_button_text) { _, _ ->
            negativeResult()
        }
        .setCancelable(false)
        .show()
}

fun Fragment.showNoInternetDialog(positiveResult: () -> Unit, negativeResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(R.string.no_internet_dialog_title)
        .setMessage(R.string.no_internet_dialog_message)
        .setPositiveButton(R.string.ok_dialog_button_text) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(R.string.cancel_dialog_button_text) { _, _ ->
            negativeResult()
        }
        .setCancelable(false)
        .show()
}

// RX

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, BiFunction { t, u -> Pair(t, u) })

fun <T : Any> Single<T>.subscribeOver(
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)