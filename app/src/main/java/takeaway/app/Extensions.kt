package takeaway.app

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, BiFunction { t, u -> Pair(t, u) })

fun <T : Any> Single<T>.subscribeOver(
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)

fun MaterialButton.enable() {
    isEnabled = true
    background.setTint(ContextCompat.getColor(context, R.color.colorAccent))
}

fun MaterialButton.disable() {
    isEnabled = false
    background.setTint(
        ContextCompat.getColor(
            context,
            R.color.disabled_button_background
        )
    )
}

fun TextInputLayout.invalidateError() {
    error = null
    isErrorEnabled = false
}

fun View.showPopup(
    itemList: List<String>,
    itemClickListener: (item: String) -> Unit
) {
    val popupMenu = PopupMenu(context, this)

    itemList.forEach { item ->
        popupMenu.menu
            .add(item)
            .setOnMenuItemClickListener {
                itemClickListener(it.toString())
                false
            }
    }

    popupMenu.show()
}

fun Fragment.addBackPressedListener(
    enabledCallback: Boolean = true,
    action: OnBackPressedCallback.() -> Unit
): OnBackPressedCallback {
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(enabledCallback) {
        override fun handleOnBackPressed() {
            action.invoke(this)
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    return callback
}

fun Fragment.makeSnackbar(hostView: View? = view, @StringRes textRes: Int): Snackbar? =
    hostView?.let { Snackbar.make(it, textRes, BaseTransientBottomBar.LENGTH_LONG) }
        ?.apply(::decorateSnackBar)

private fun Fragment.decorateSnackBar(snackBar: Snackbar) {
    with(snackBar) {
        ViewCompat.setElevation(view, resources.getDimension(R.dimen.spacing_2))
    }
}