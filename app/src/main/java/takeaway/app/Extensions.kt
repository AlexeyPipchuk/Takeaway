package takeaway.app

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.hideKeyboard() =
    requireActivity().window.decorView.hideKeyboard()

fun View.hideKeyboard() {
    context?.let { context ->
        val inputManager = context.getSystemService<InputMethodManager>()
        inputManager?.hideSoftInputFromWindow(windowToken, 0)
        inputManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
    } ?: return
}

fun String.fromHtml(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }

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