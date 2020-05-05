package takeaway.app

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

val Fragment.args: Bundle
    get() = arguments
        ?: throw IllegalArgumentException("Fragment has no arguments")

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

fun Fragment.showSeveralCafeBasketWarningDialog(positiveResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(getString(R.string.several_cafe_basket_warning_title))
        .setMessage(getString(R.string.several_cafe_basket_warning_message))
        .setPositiveButton(getString(R.string.several_cafe_basket_warning_clear_approve)) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(getString(R.string.several_cafe_basket_warning_cancel_clear)) { _, _ ->
            Unit
        }
        .setCancelable(false)
        .show()
}

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, BiFunction { t, u -> Pair(t, u) })

fun <T : Any> Single<T>.subscribeOver(
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)