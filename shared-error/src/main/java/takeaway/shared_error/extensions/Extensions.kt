package takeaway.shared_error.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import takeaway.shared_error.R

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